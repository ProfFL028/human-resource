package com.smnsyh.hr.auth.provider

import com.smnsyh.hr.auth.BackendAdminUsernamePasswordAuthenticationToken
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.AuthorityUtils

class BackendAdminUsernamePasswordAuthenticationProvider : AuthenticationProvider {

    @Value("\${backend.admin.username}")
    private lateinit var backendUsername: String

    @Value("\${backend.admin.password}")
    private lateinit var backendPassword: String

    override fun authenticate(authentication: Authentication): Authentication {
        val username: String? = authentication.principal.toString()
        val password: String? = authentication.credentials.toString()

        if (username == null || password == null) {
            throw BadCredentialsException("Invalid Backend Admin Credentials")
        }

        if (backendUsername != username || backendPassword != password) {
            throw BadCredentialsException("Invalid Backend Admin Credentials")
        }

        return UsernamePasswordAuthenticationToken(username, null,
                AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_BACKEND_ADMIN"))
    }

    override fun supports(authentication: Class<*>?): Boolean {
        return authentication == BackendAdminUsernamePasswordAuthenticationToken::class.java
    }

}
