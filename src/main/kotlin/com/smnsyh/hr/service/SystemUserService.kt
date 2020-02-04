package com.smnsyh.hr.service

import com.smnsyh.hr.auth.AuthenticationWithToken
import com.smnsyh.hr.repository.SystemUserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.stereotype.Service

@Service
class SystemUserService {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(SystemUserService::class.java)
    }

    @Autowired
    private lateinit var systemUserRepository: SystemUserRepository

}

