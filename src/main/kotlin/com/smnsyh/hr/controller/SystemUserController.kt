package com.smnsyh.hr.controller

import com.smnsyh.hr.entity.SystemUser
import com.smnsyh.hr.repository.SystemUserRepository
import org.springframework.web.bind.annotation.*

@RestController
class SystemUserController(
        val systemUserRepository: SystemUserRepository
) {

    @GetMapping(ApiController.SYSTEM_USER_URL)
    fun getUsers(): Iterable<SystemUser> {
        return this.systemUserRepository.findAll()
    }

    @PostMapping(ApiController.SYSTEM_USER_URL)
    fun addUser(@RequestBody user: SystemUser) {
        this.systemUserRepository.save(user)
    }

}
