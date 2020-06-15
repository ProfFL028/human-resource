package com.smnsyh.hr.controller

import com.smnsyh.hr.dto.UserDto
import com.smnsyh.hr.entity.SystemUser
import com.smnsyh.hr.repository.SystemUserRepository
import com.smnsyh.hr.service.SystemUserService
import org.springframework.web.bind.annotation.*

@RestController
class SystemUserController(
        val systemUserService: SystemUserService
) {

    @GetMapping(ApiController.SYSTEM_USER_URL + "/{page}/{size}")
    fun getUsers(@PathVariable page: Int, @PathVariable size: Int): Iterable<UserDto> {
        return this.systemUserService.findUsers(page, size)
    }

    @PostMapping(ApiController.SYSTEM_USER_URL)
    fun addUser(@RequestBody user: UserDto) {
        this.systemUserService.save(user)
    }

}
