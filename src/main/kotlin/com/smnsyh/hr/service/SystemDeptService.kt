package com.smnsyh.hr.service

import com.smnsyh.hr.dojo.SystemDeptTreeNode
import com.smnsyh.hr.repository.SystemDeptRepository
import org.springframework.stereotype.Service

@Service
class SystemDeptService(
        var systemDeptRepository: SystemDeptRepository
) {
    fun getDeptTree(): Iterable<SystemDeptTreeNode> {
        var systemDeptTree = ArrayList<SystemDeptTreeNode>()

        var systemDepts = systemDeptRepository.findAll()

        for (systemDept in systemDepts) {

        }
        systemDeptTree.add(SystemDeptTreeNode(systemDepts))

        return systemDeptTree
    }
}
