package com.smnsyh.hr.auth.provider

import com.smnsyh.hr.auth.TokenCacheService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken

class TokenAuthenticationProvider : AuthenticationProvider {

    @Autowired
    private lateinit var tokenCacheService: TokenCacheService

    override fun authenticate(authentication: Authentication): Authentication? {
        val token: String? = authentication.principal.toString()
        if (token == null || token.isEmpty()) {
            throw BadCredentialsException("Invalid token")
        }
        if (!tokenCacheService.contains(token)) {
            throw BadCredentialsException("Invalid token or token Expired")
        }

        return tokenCacheService.retrieve(token)
    }

    override fun supports(authentication: Class<*>?): Boolean {
        return PreAuthenticatedAuthenticationToken::class.java == authentication
    }

}
