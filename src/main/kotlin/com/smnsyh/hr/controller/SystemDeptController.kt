package com.smnsyh.hr.controller

import com.smnsyh.hr.dojo.SystemDeptTreeNode
import com.smnsyh.hr.entity.SystemDept
import com.smnsyh.hr.entity.SystemPosition
import com.smnsyh.hr.repository.SystemDeptRepository
import com.smnsyh.hr.service.SystemDeptService
import org.springframework.web.bind.annotation.*

@RestController
class SystemDeptController(
        val systemDeptRepository: SystemDeptRepository,
        var systemDeptService: SystemDeptService
) {

    @GetMapping(ApiController.SYSTEM_DEPT_URL)
    fun findAll(): Iterable<SystemDeptTreeNode> {
        return this.systemDeptService.getDeptTree()
    }

    @GetMapping(ApiController.SYSTEM_DEPT_URL + "/options")
    fun findSystemDeptOptions(): Iterable<SystemDept> {
        return this.systemDeptRepository.findAll()
    }

    @GetMapping(ApiController.SYSTEM_DEPT_URL + "/benjiOptions")
    fun findSystemDeptBenjiOptions(): Iterable<SystemDept> {
        return this.systemDeptRepository.findByDeptNumberStartsWith("J")
    }

    @PostMapping(ApiController.SYSTEM_DEPT_URL)
    fun save(@RequestBody systemDept: SystemDept) : SystemDept {
        return this.systemDeptRepository.save(systemDept)
    }

    @PostMapping(ApiController.SYSTEM_DEPT_URL + "/modifyPositions/{deptId}")
    fun modifyPositions(@PathVariable deptId: Short, @RequestBody systemPositions: List<SystemPosition>) {
        this.systemDeptService.modifyPosition(deptId, systemPositions)
    }

    @DeleteMapping(ApiController.SYSTEM_DEPT_URL + "/{id}")
    fun delete(@PathVariable id: Short) {
        this.systemDeptRepository.deleteById(id)
    }
}
