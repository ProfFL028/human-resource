package com.smnsyh.hr.auth.provider

import com.smnsyh.hr.auth.AuthenticationWithToken
import com.smnsyh.hr.auth.TokenCacheService
import com.smnsyh.hr.service.AuthenticationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

class DomainUsernamePasswordAuthenticationProvider : AuthenticationProvider {

    private val tokenCacheService: TokenCacheService

    @Autowired
    private lateinit var authenticationService: AuthenticationService

    constructor(tokenCacheService: TokenCacheService) {
        this.tokenCacheService = tokenCacheService
    }

    override fun authenticate(authentication: Authentication): Authentication {
        val username: String? = authentication.principal.toString()
        val password: String? = authentication.credentials.toString()

        if (username == null || password == null) {
            throw BadCredentialsException("Invalid Domain User Credentials")
        }

        val resultOfAuthentication: AuthenticationWithToken = authenticationService.login(username, password)
                ?: throw BadCredentialsException("Invalid Username or Password Credentials")
        val newToken = tokenCacheService.generateNewToken()
        resultOfAuthentication.token = newToken
        tokenCacheService.store(newToken, resultOfAuthentication)
        return resultOfAuthentication
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    override fun supports(authentication: Class<*>): Boolean {
        return authentication == UsernamePasswordAuthenticationToken::class.java
    }

}
