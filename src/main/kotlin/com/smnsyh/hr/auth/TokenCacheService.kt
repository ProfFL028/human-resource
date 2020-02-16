package com.smnsyh.hr.auth

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.HashMap

@Service
class TokenCacheService {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(TokenCacheService::class.java)
        const val AN_HOUR_IN_MILLSECOND: Long = 60 * 60 * 1000

        val restApiAuthTokenCache = HashMap<String, Authentication>()
        val restApiAuthTokenCachedTime = HashMap<String, LocalDateTime>()
    }

    @Scheduled(fixedRate = AN_HOUR_IN_MILLSECOND)
    fun evictExpiredTokens() {
        logger.info("Evicting expired tokens")
        for (key in restApiAuthTokenCachedTime.keys) {
            var durationBetweenNow = Duration.between(LocalDateTime.now(), restApiAuthTokenCachedTime[key])
            if (durationBetweenNow.seconds >= AN_HOUR_IN_MILLSECOND/1000) {
                restApiAuthTokenCache.remove(key)
                restApiAuthTokenCachedTime.remove(key)
            }
        }
    }

    fun generateNewToken(): String {
        return UUID.randomUUID().toString()
    }

    fun store(token: String, authentication: Authentication) {
        restApiAuthTokenCache[token] = authentication
        restApiAuthTokenCachedTime[token] = LocalDateTime.now()
    }

    fun contains(token: String): Boolean {
        return restApiAuthTokenCache[token] != null
    }

    fun retrieve(token: String): Authentication? {
        if (contains(token)) {
            return restApiAuthTokenCache[token];
        }
        return null
    }
}
