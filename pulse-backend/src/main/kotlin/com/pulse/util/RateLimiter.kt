package com.pulse.util

import kotlinx.coroutines.delay
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class RateLimiter(private val permitsPerSecond: Int) {
    private val mutex = Mutex()
    private var tokens = permitsPerSecond.toDouble()
    private var lastRefillTime = System.currentTimeMillis()

    suspend fun acquire() {
        mutex.withLock {
            refill()
            if (tokens < 1.0) {
                val timeToWait = ((1.0 - tokens) * 1000 / permitsPerSecond).toLong()
                delay(timeToWait)
                refill()
            }
            tokens -= 1.0
        }
    }

    private fun refill() {
        val now = System.currentTimeMillis()
        val elapsedTime = now - lastRefillTime
        tokens += elapsedTime * (permitsPerSecond / 1000.0)
        if (tokens > permitsPerSecond) {
            tokens = permitsPerSecond.toDouble()
        }
        lastRefillTime = now
    }
}
