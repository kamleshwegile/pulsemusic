package com.pulse.util

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

enum class CircuitState { CLOSED, OPEN, HALF_OPEN }

class CircuitBreaker(
    private val failureThreshold: Int = 5,
    private val resetTimeoutMillis: Long = 60_000
) {
    var state = CircuitState.CLOSED
        private set
    private var failureCount = 0
    private var lastFailureTime = 0L
    private val mutex = Mutex()

    suspend fun <T> execute(block: suspend () -> T): T {
        mutex.withLock {
            if (state == CircuitState.OPEN) {
                if (System.currentTimeMillis() - lastFailureTime > resetTimeoutMillis) {
                    state = CircuitState.HALF_OPEN
                } else {
                    throw IllegalStateException("Circuit breaker is OPEN")
                }
            }
        }

        return try {
            val result = block()
            onSuccess()
            result
        } catch (e: Exception) {
            onFailure()
            throw e
        }
    }

    private suspend fun onSuccess() {
        mutex.withLock {
            failureCount = 0
            state = CircuitState.CLOSED
        }
    }

    private suspend fun onFailure() {
        mutex.withLock {
            failureCount++
            lastFailureTime = System.currentTimeMillis()
            if (failureCount >= failureThreshold) {
                state = CircuitState.OPEN
            }
        }
    }
}
