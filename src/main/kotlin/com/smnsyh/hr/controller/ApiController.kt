package com.smnsyh.hr.controller

abstract class ApiController {
    companion object {
        private const val API_PATH = "/api/v1"

        const val AUTHENTICATE_URL: String = "$API_PATH/auth"
        const val SYSTEM_USER_URL: String = "$API_PATH/user"
        const val SYSTEM_ROLE_URL: String = "$API_PATH/role"
        const val SYSTEM_POSITION_URL: String = "$API_PATH/position"

        // Spring Boot Actuator services
        const val AUTOCONFIG_ENDPOINT = "/autoconfig"
        const val BEANS_ENDPOINT = "/beans"
        const val CONFIGPROPS_ENDPOINT = "/configprops"
        const val ENV_ENDPOINT = "/env"
        const val MAPPINGS_ENDPOINT = "/mappings"
        const val METRICS_ENDPOINT = "/metrics"
        const val SHUTDOWN_ENDPOINT = "/shutdown"
    }
}
