package com.smnsyh.hr.config

import com.smnsyh.hr.auth.*
import com.smnsyh.hr.auth.filter.AuthenticationFilter
import com.smnsyh.hr.auth.filter.ManagementEndpointAuthenticationFilter
import com.smnsyh.hr.auth.provider.BackendAdminUsernamePasswordAuthenticationProvider
import com.smnsyh.hr.auth.provider.DomainUsernamePasswordAuthenticationProvider
import com.smnsyh.hr.auth.provider.TokenAuthenticationProvider
import com.smnsyh.hr.controller.ApiController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Configuration
@EnableWebSecurity
@EnableScheduling
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfig : WebSecurityConfigurerAdapter() {

    @Value("\${backend.admin.role}")
    private lateinit var backendAdminRole: String

    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(*actuatorEndpoints()).hasRole(backendAdminRole)
                .anyRequest().authenticated()
                .and()
                .anonymous().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint())

        http.addFilterBefore(AuthenticationFilter(authenticationManager()), BasicAuthenticationFilter::class.java)
                .addFilterBefore(ManagementEndpointAuthenticationFilter(authenticationManager()), BasicAuthenticationFilter::class.java)
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(domainUsernamePasswordAuthenticationProvider())
                .authenticationProvider(backendAdminUsernamePasswordAuthenticationProvider())
                .authenticationProvider(tokenAuthenticationProvider())
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val corsConfiguration = CorsConfiguration()
        corsConfiguration.allowedOrigins = listOf("http://localhost:4200")
        corsConfiguration.allowedHeaders = listOf("*")
        corsConfiguration.allowedMethods = listOf("GET", "POST")
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", corsConfiguration)
        return source
    }


    @Bean
    fun backendAdminUsernamePasswordAuthenticationProvider(): AuthenticationProvider {
        return BackendAdminUsernamePasswordAuthenticationProvider()
    }

    @Autowired
    private lateinit var tokenCacheService: TokenCacheService

    @Bean
    fun domainUsernamePasswordAuthenticationProvider(): AuthenticationProvider {
        return DomainUsernamePasswordAuthenticationProvider(tokenCacheService)
    }

    @Bean
    fun tokenAuthenticationProvider(): AuthenticationProvider {
        return TokenAuthenticationProvider()
    }

    @Bean
    fun unauthorizedEntryPoint(): AuthenticationEntryPoint {
        return AuthenticationEntryPoint { _: HttpServletRequest,
                                          response: HttpServletResponse,
                                          _: AuthenticationException ->
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED)
        }
    }

    private fun actuatorEndpoints(): Array<String> {
        return arrayOf(ApiController.AUTOCONFIG_ENDPOINT, ApiController.BEANS_ENDPOINT, ApiController.CONFIGPROPS_ENDPOINT,
                ApiController.ENV_ENDPOINT, ApiController.MAPPINGS_ENDPOINT,
                ApiController.METRICS_ENDPOINT, ApiController.SHUTDOWN_ENDPOINT)
    }
}
