package com.pulse.repository

import com.pulse.providers.MusicProvider
import com.pulse.models.ProviderStatus
import kotlinx.coroutines.withTimeout
import org.slf4j.LoggerFactory
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentLinkedQueue

class ProviderManager(
    val providers: List<MusicProvider>,
    private val cacheRepository: CacheRepository
) {
    private val logger = LoggerFactory.getLogger(ProviderManager::class.java)
    
    // Sliding window of last 10 calls for each provider (true = success, false = failure)
    private val failureWindows = ConcurrentHashMap<String, ConcurrentLinkedQueue<Boolean>>()
    private val disabledUntil = ConcurrentHashMap<String, Long>()
    
    init {
        providers.forEach { 
            failureWindows[it.name] = ConcurrentLinkedQueue()
            disabledUntil[it.name] = 0L
        }
    }
    
    private fun recordResult(providerName: String, success: Boolean) {
        val window = failureWindows[providerName] ?: return
        window.add(success)
        while (window.size > 10) {
            window.poll()
        }
        
        val failures = window.count { !it }
        if (window.size >= 8 && failures.toFloat() / window.size > 0.8f) {
            disabledUntil[providerName] = System.currentTimeMillis() + 30 * 1000 // 30 seconds
            logger.warn("Provider $providerName auto-disabled due to high failure rate.")
            window.clear()
        }
    }
    
    private fun isProviderAvailable(providerName: String): Boolean {
        val disabledTime = disabledUntil[providerName] ?: 0L
        return System.currentTimeMillis() > disabledTime
    }

    suspend fun <T> executeWithFailover(operationName: String, cacheKey: String?, operation: suspend (MusicProvider) -> T?): Pair<T?, String> {
        val sortedProviders = providers.sortedBy { it.priority }
        var lastException: Exception? = null
        
        for (provider in sortedProviders) {
            if (!isProviderAvailable(provider.name)) continue
            
            try {
                val result = withTimeout(10_000) {
                    operation(provider)
                }
                if (result != null) {
                    recordResult(provider.name, true)
                    return Pair(result, provider.name)
                }
            } catch (e: Exception) {
                logger.error("Provider ${provider.name} failed for $operationName", e)
                recordResult(provider.name, false)
                lastException = e
            }
        }
        
        logger.error("All providers failed for $operationName.", lastException)
        return Pair(null, "None")
    }
    
    fun getHealth(): List<ProviderStatus> {
        return providers.map { provider ->
            val window = failureWindows[provider.name]
            val failureRate = if (window != null && window.isNotEmpty()) {
                window.count { !it }.toFloat() / window.size
            } else 0f
            
            ProviderStatus(
                name = provider.name,
                healthy = provider.isHealthy(),
                failureRate = failureRate,
                lastCheck = System.currentTimeMillis(),
                disabled = !isProviderAvailable(provider.name)
            )
        }
    }
}
