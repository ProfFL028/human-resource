package com.smnsyh.hr.controller

import com.smnsyh.hr.vo.RoleVO
import com.smnsyh.hr.entity.SystemRole
import com.smnsyh.hr.service.SystemRoleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.Validator

@RestController
class SystemRoleController(private val systemRoleService: SystemRoleService) {

    @GetMapping(ApiController.SYSTEM_ROLE_URL)
    fun findAll(): Iterable<RoleVO> {

        return systemRoleService.findAll()
    }

    @PostMapping(ApiController.SYSTEM_ROLE_URL)
    fun save(@RequestBody @Valid systemRole: SystemRole): SystemRole {
        return systemRoleService.save(systemRole)
    }

    @PostMapping("${ApiController.SYSTEM_ROLE_URL}/status/{id}" )
    fun toggleStatus(@PathVariable("id") id: Short) {
        systemRoleService.toggleStatus(id)
    }

    @DeleteMapping("${ApiController.SYSTEM_ROLE_URL}/{id}")
    fun delete(@PathVariable("id") id: Short) {
        systemRoleService.deleteById(id)
    }
}
