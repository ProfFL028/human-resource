package com.smnsyh.hr.controller

import com.smnsyh.hr.entity.SystemUser
import com.smnsyh.hr.repository.SystemUserRepository
import org.springframework.web.bind.annotation.*

@RestController
class SystemUserController(
        val systemUserRepository: SystemUserRepository
) {

    @GetMapping("/users")
    fun getUsers(): List<SystemUser> {
        return this.systemUserRepository.findAll() as List<SystemUser>
    }

    @PostMapping("/users")
    fun addUser(@RequestBody user: SystemUser) {
        this.systemUserRepository.save(user)
    }
}
