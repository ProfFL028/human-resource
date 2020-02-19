package com.smnsyh.hr.service

import com.smnsyh.hr.auth.AuthenticationWithToken
import com.smnsyh.hr.dojo.UserDetail
import com.smnsyh.hr.entity.SystemUser
import com.smnsyh.hr.repository.SystemMenuRepository
import com.smnsyh.hr.repository.SystemUserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Suppress("SpringConfigurationProxyMethods")
@Service
class AuthenticationService {

    @Autowired
    private lateinit var systemUserRepository: SystemUserRepository

    @Autowired
    private lateinit var systemMenuRepository: SystemMenuRepository

    fun login(username: String, password: String): AuthenticationWithToken? {
        var resultOfAuthentication: AuthenticationWithToken? = null
        val passwordInDb: String = systemUserRepository.getPasswordBy(username)

        // For test purpose
        if (true) {
        //if (passwordInDb.isNotEmpty() && passwordEncoderProvider().matches(password, passwordInDb)) {
            resultOfAuthentication = AuthenticationWithToken(username, "")

            val userDetail = getSystemUserDetails(username)
            resultOfAuthentication.details = userDetail
            resultOfAuthentication.isAuthenticated = true
    }
        return resultOfAuthentication
    }

    private fun getSystemUserDetails(tellerNumber: String): UserDetail {
        var systemUser: SystemUser? =  systemUserRepository.findById(tellerNumber).orElse(null)
        var userDetail = UserDetail()
        if (systemUser != null) {
            userDetail = UserDetail.fromSystemUser(systemUser)
            val availableMenus = systemMenuRepository.findAvailableMenusTo(systemUser)
            userDetail.setAvailableMenus(availableMenus)
        }

        return userDetail
    }


    @Bean
    fun passwordEncoderProvider(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }
}
