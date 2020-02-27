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

        var systemDepts = systemDeptRepository.findAllByOrderByParent()

        for (systemDept in systemDepts) {
            var systemDeptTreeNode = SystemDeptTreeNode(systemDept, true)
            if (systemDept.parent == null) {
                systemDeptTree.add(systemDeptTreeNode)
            } else {
                for (systemDeptTreeNode in systemDeptTree) {
                    systemDeptTreeNode.addNodeIfMyChild(systemDept)
                }
            }

        }

        return systemDeptTree
    }
}
