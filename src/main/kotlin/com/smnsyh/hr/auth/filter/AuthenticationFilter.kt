package com.smnsyh.hr.auth.filter

import com.fasterxml.jackson.databind.ObjectMapper
import com.smnsyh.hr.auth.TokenResponse
import com.smnsyh.hr.controller.ApiController
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.InternalAuthenticationServiceException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken
import org.springframework.util.StringUtils
import org.springframework.web.filter.GenericFilterBean
import org.springframework.web.util.UrlPathHelper
import javax.naming.AuthenticationException
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthenticationFilter : GenericFilterBean {
    private val authenticationManager: AuthenticationManager

    constructor(authenticationManager: AuthenticationManager) {
        this.authenticationManager = authenticationManager
    }

    companion object {
        val logger: Logger = LoggerFactory.getLogger(AuthenticationFilter::class.java)
        const val TOKEN_SESSION_KEY: String = "TOKEN"
        const val USER_SESSION_KEY: String = "USER"
    }

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val httpRequest = request as HttpServletRequest
        val httpResponse = response as HttpServletResponse

        val username: String? = httpRequest.getHeader("X-Auth-Username")
        val password: String? = httpRequest.getHeader("X-Auth-Password")
        val token: String? = httpRequest.getHeader("X-Auth-Token")

        val resourcePath: String = UrlPathHelper().getPathWithinApplication(httpRequest)

        try {
            if (postToAuthenticate(httpRequest, resourcePath)) {
                Companion.logger.info("Trying to authenticate user {$username} by X-Auth-Username method")
                processUsernamePasswordAuthentication(httpResponse, username, password)
                return
            }

            if (token != null && token.isNotEmpty()) {
                Companion.logger.info("Trying to authenticate user by X-Auth-Token method, token: $token")
                processTokenAuthentication(token)
            }

            Companion.logger.debug("AuthenticationFilter is passing request down the filter chain")
            addSessionContextToLogging()
            chain.doFilter(request, response)
        } catch (internalAuthenticationServiceException: InternalAuthenticationServiceException) {
            SecurityContextHolder.clearContext();
            Companion.logger.error("Internal authentication service Exception", internalAuthenticationServiceException)
            httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR)
        } catch (authenticationException: AuthenticationException) {
            SecurityContextHolder.clearContext()
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, authenticationException.message)
        } catch (badCredentialException: BadCredentialsException) {
            SecurityContextHolder.clearContext()
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, badCredentialException.message)
        } finally {
            MDC.remove(TOKEN_SESSION_KEY)
            MDC.remove(USER_SESSION_KEY)
        }
    }

    private fun addSessionContextToLogging() {
        val authentication: Authentication? = SecurityContextHolder.getContext().authentication
        var tokenValue = "EMPTY"

        if (authentication != null && StringUtils.isEmpty(authentication.details.toString())) {
            val encoder = BCryptPasswordEncoder()
            tokenValue = encoder.encode(authentication.details.toString())
        }
        MDC.put(TOKEN_SESSION_KEY, tokenValue)

        var userValue = "EMPTY"
        if (authentication != null && StringUtils.isEmpty(authentication.principal.toString())) {
            userValue = authentication.principal.toString()
        }
        MDC.put(USER_SESSION_KEY, userValue)
    }

    private fun postToAuthenticate(request: HttpServletRequest, resourcePath: String): Boolean {
        return ApiController.AUTHENTICATE_URL.equals(resourcePath, true) && request.method == "POST"
    }

    private fun processUsernamePasswordAuthentication(httpResponse: HttpServletResponse, username: String?, password: String?) {
        val resultOfAuthentication: Authentication = tryToAuthenticateWithUsernameAndPassword(username, password)
        SecurityContextHolder.getContext().authentication = resultOfAuthentication
        httpResponse.status = HttpServletResponse.SC_OK

        val jsonResponse: String = ObjectMapper().writeValueAsString(resultOfAuthentication)
        httpResponse.addHeader("Content-Type", "application/json")
        httpResponse.writer.print(jsonResponse)
    }

    private fun processTokenAuthentication(token: String) {
        val resultOfAuthentication: Authentication = tryToAuthenticateWithToken(token)
        SecurityContextHolder.getContext().authentication = resultOfAuthentication
    }

    private fun tryToAuthenticateWithToken(token: String?): Authentication {
        val requestAuthentication = PreAuthenticatedAuthenticationToken(token, null)
        return tryToAuthenticate(requestAuthentication)
    }

    private fun tryToAuthenticateWithUsernameAndPassword(username: String?, password: String?): Authentication {
        val requestAuthentication = UsernamePasswordAuthenticationToken(username, password)
        return tryToAuthenticate(requestAuthentication)
    }

    private fun tryToAuthenticate(requestAuthentication: Authentication): Authentication {
        val responseAuthentication = authenticationManager.authenticate(requestAuthentication)
        println(responseAuthentication)
        if (responseAuthentication == null || !responseAuthentication.isAuthenticated) {
            throw InternalAuthenticationServiceException("Unable to authenticate Domain User for provided credentials")
        }
        return responseAuthentication
    }
}
