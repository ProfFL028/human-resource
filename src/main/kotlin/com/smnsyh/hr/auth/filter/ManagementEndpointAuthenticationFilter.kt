package com.smnsyh.hr.auth.filter

import com.smnsyh.hr.auth.BackendAdminUsernamePasswordAuthenticationToken
import com.smnsyh.hr.controller.ApiController
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.InternalAuthenticationServiceException
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean
import org.springframework.web.util.UrlPathHelper
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class ManagementEndpointAuthenticationFilter : GenericFilterBean {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(ManagementEndpointAuthenticationFilter::class.java)
    }

    private val authenticationManager: AuthenticationManager
    private var managementEndpoints: HashSet<String> = HashSet()

    constructor(authenticationManager: AuthenticationManager) {
        this.authenticationManager = authenticationManager
        prepareManagementEndPointsSet()
    }

    private fun prepareManagementEndPointsSet() {
        managementEndpoints.add(ApiController.AUTOCONFIG_ENDPOINT)
        managementEndpoints.add(ApiController.BEANS_ENDPOINT)
        managementEndpoints.add(ApiController.CONFIGPROPS_ENDPOINT)
        managementEndpoints.add(ApiController.ENV_ENDPOINT)
        managementEndpoints.add(ApiController.MAPPINGS_ENDPOINT)
        managementEndpoints.add(ApiController.METRICS_ENDPOINT)
        managementEndpoints.add(ApiController.SHUTDOWN_ENDPOINT)
    }

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val httpRequest = request as HttpServletRequest
        val httpResponse = response as HttpServletResponse

        val username: String? = httpRequest.getHeader("X-Auth-Username")
        val password: String? = httpRequest.getHeader("X-Auth-Password")

        val resourcePath: String = UrlPathHelper().getPathWithinApplication(httpRequest)

        try {
            if (postToManagementEndpoints(resourcePath)) {
                Companion.logger.debug("Trying to authenticate user $username for management endpoint by X-Auth-Username method")
                processManagementEndpointUsernamePasswordAuthentication(username, password);
            }
            logger.debug("ManagementEndpointAuthenticationFilter is passing request down the filter chain")
            chain.doFilter(request, response)
        } catch (authenticationException: AuthenticationException) {
            SecurityContextHolder.clearContext();
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, authenticationException.message)
        }
    }

    private fun postToManagementEndpoints(resourcePath: String): Boolean {
        return managementEndpoints.contains(resourcePath)
    }

    private fun processManagementEndpointUsernamePasswordAuthentication(username: String?, password: String?): Authentication {
        val requestAuthentication = BackendAdminUsernamePasswordAuthenticationToken(username, password)
        return tryToAuthenticate(requestAuthentication)
    }

    private fun tryToAuthenticate(authentication: Authentication): Authentication {
        val responseAuthentication = authenticationManager.authenticate(authentication)
        if (responseAuthentication == null || !responseAuthentication.isAuthenticated){
            throw InternalAuthenticationServiceException("Unable to authenticate Backend Admin for provided credentials")
        }
        logger.debug("Backend Admin Successfully authenticated")

        return responseAuthentication
    }

}
