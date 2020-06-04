package com.smnsyh.hr.service

import com.smnsyh.hr.dojo.SystemDeptTreeNode
import com.smnsyh.hr.entity.SystemDept
import com.smnsyh.hr.entity.SystemPosition
import com.smnsyh.hr.repository.SystemDeptRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Service
class SystemDeptService(
        var systemDeptRepository: SystemDeptRepository

) {
    @PersistenceContext
    private lateinit var entityManager: EntityManager

    @Transactional(readOnly = true)
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

    @Transactional
    fun modifyPosition(deptId: Short, systemPositions: List<SystemPosition>) {
        var deleteOldPositionsSql = "delete from system_dept_positions where dept_id=?"
        this.entityManager.createNativeQuery(deleteOldPositionsSql).setParameter(1, deptId).executeUpdate()
        for (systemPosition in systemPositions) {
            var addPositionSql = "insert into system_dept_positions(dept_id, position_id) values(?, ?)"
            this.entityManager.createNativeQuery(addPositionSql)
                    .setParameter(1, deptId)
                    .setParameter(2, systemPosition.id)
                    .executeUpdate()
        }
    }

    @Transactional
    fun save(systemDept: SystemDept): SystemDept {
        if (systemDept.parent == null || systemDept.parent?.id == null) {
            systemDept.parent = null
        }
        return this.systemDeptRepository.save(systemDept)
    }
}
