package com.smnsyh.hr.auth

import net.sf.ehcache.Cache
import net.sf.ehcache.CacheManager
import net.sf.ehcache.Element
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import java.util.*

@Service
class TokenCacheService {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(TokenCacheService::class.java)
        const val HALF_AN_HOUR_IN_MILLISECONDS: Long = 60 * 60 * 1000

        val restApiAuthTokenCache: Cache = CacheManager.getInstance().getCache("restApiAuthTokenCache")
    }

    @Scheduled(fixedRate = HALF_AN_HOUR_IN_MILLISECONDS)
    fun evictExpiredTokens() {
        logger.info("Evicting expired tokens")
        restApiAuthTokenCache.evictExpiredElements()
    }

    fun generateNewToken(): String {
        return UUID.randomUUID().toString()
    }

    fun store(token: String, authentication: Authentication) {
        restApiAuthTokenCache.put(Element(token, authentication))
    }

    fun contains(token: String): Boolean {
        return restApiAuthTokenCache.get(token) != null
    }

    fun retrieve(token: String): Authentication? {
        if (contains(token)) {
            return restApiAuthTokenCache.get(token).objectValue as Authentication
        }
        return null
    }
}
