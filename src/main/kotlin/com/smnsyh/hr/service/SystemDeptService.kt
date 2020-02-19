package com.smnsyh.hr.service

import com.smnsyh.hr.dojo.SystemDeptTree
import com.smnsyh.hr.repository.SystemDeptRepository
import org.springframework.stereotype.Service

@Service
class SystemDeptService(
        var systemDeptRepository: SystemDeptRepository
) {
    fun getDeptTree(): Iterable<SystemDeptTree> {
        var systemDeptTree = ArrayList<SystemDeptTree>()
        var systemDepts = systemDeptRepository.findAll()
        systemDeptTree.add(SystemDeptTree(systemDepts))

        return systemDeptTree
    }
}
