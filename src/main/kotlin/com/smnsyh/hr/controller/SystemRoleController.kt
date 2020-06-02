package com.smnsyh.hr.controller

import com.smnsyh.hr.dto.RoleDto
import com.smnsyh.hr.entity.SystemRole
import com.smnsyh.hr.repository.SystemRoleRepository
import com.smnsyh.hr.service.SystemRoleService
import org.springframework.web.bind.annotation.*

@RestController
class SystemRoleController(private val systemRoleService: SystemRoleService, val systemRoleRepository: SystemRoleRepository) {

    @GetMapping(ApiController.SYSTEM_ROLE_URL)
    fun findAll(): Iterable<RoleDto> {

        return systemRoleService.findAll()
    }

    @PostMapping(ApiController.SYSTEM_ROLE_URL)
    fun save(@RequestBody systemRole: SystemRole): SystemRole {
        return systemRoleRepository.save(systemRole)
    }

    @PostMapping("${ApiController.SYSTEM_ROLE_URL}/status/{id}" )
    fun toggleStatus(@PathVariable("id") id: Short) {
        systemRoleService.toggleStatus(id)
    }

    @DeleteMapping("${ApiController.SYSTEM_ROLE_URL}/{id}")
    fun delete(@PathVariable("id") id: Short) {
        systemRoleRepository.deleteById(id)
    }
}
