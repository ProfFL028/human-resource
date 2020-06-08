package com.smnsyh.hr.service

import com.smnsyh.hr.repository.SystemUserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class SystemUserService(
        val systemUserRepository: SystemUserRepository
) {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(SystemUserService::class.java)
    }

    fun findUsers(pageable: Pageable) {
        
    }
}

