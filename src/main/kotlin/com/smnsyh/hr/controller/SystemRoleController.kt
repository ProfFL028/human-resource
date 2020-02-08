package com.smnsyh.hr.controller

import com.smnsyh.hr.entity.SystemRole
import com.smnsyh.hr.repository.SystemRoleRepository
import org.springframework.web.bind.annotation.*

@RestController
class SystemRoleController(val systemRoleRepository: SystemRoleRepository) {

    @GetMapping(ApiController.SYSTEM_ROLE_URL)
    fun findAll(): Iterable<SystemRole> {
        return systemRoleRepository.findAll()
    }

    @PostMapping(ApiController.SYSTEM_ROLE_URL)
    fun save(@RequestBody systemRole: SystemRole): SystemRole {
        return systemRoleRepository.save(systemRole)
    }

    @DeleteMapping("${ApiController.SYSTEM_ROLE_URL}/{id}")
    fun delete(@PathVariable("id") id: Short) {
        systemRoleRepository.deleteById(id)
    }
}
