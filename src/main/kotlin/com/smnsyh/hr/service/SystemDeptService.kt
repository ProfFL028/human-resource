package com.smnsyh.hr.service

import com.smnsyh.hr.dojo.SystemDeptTreeNode
import com.smnsyh.hr.dto.DeptDto
import com.smnsyh.hr.entity.SystemDept
import com.smnsyh.hr.entity.SystemPosition
import com.smnsyh.hr.repository.SystemDeptRepository
import com.smnsyh.hr.repository.SystemPositionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Service
class SystemDeptService(
        val systemDeptRepository: SystemDeptRepository,
        val systemPositionRepository: SystemPositionRepository,
        @PersistenceContext
        val entityManager: EntityManager
) {

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
        var dept = this.systemDeptRepository.findById(deptId).orElse(null)
        dept.removePositions()
        systemPositions.map { p -> this.systemPositionRepository.findById(p.id!!.toShort()) }.forEach { p -> dept.addPosition(p.get()) }

        this.systemDeptRepository.save(dept)
    }

    @Transactional
    fun save(systemDept: SystemDept): SystemDept {
        if (systemDept.parent == null || systemDept.parent?.id == null) {
            systemDept.parent = null
        }
        if (systemDept.id == null) {
            return this.systemDeptRepository.save(systemDept)
        } else {
            var query = entityManager.createQuery("update SystemDept d set d.deptNumber=:deptNumber, d.fullName=:fullName, d.shortName=:shortName, d.sortNumber=:sortNumber, d.beginDate=:beginDate, d.endDate=:endDate, d.parent=:parent where d.id=:id")
            query.setParameter("deptNumber", systemDept.deptNumber)
            query.setParameter("fullName", systemDept.fullName)
            query.setParameter("shortName", systemDept.shortName)
            query.setParameter("sortNumber", systemDept.sortNumber)
            query.setParameter("parent", systemDept.parent)
            query.setParameter("beginDate", systemDept.beginDate)
            query.setParameter("endDate", systemDept.endDate)
            query.setParameter("id", systemDept.id)
            query.executeUpdate()
        }
        return systemDept
    }


    @Transactional
    fun deleteById(id: Short) {
        this.systemDeptRepository.deleteById(id)
    }

    @Transactional(readOnly = true)
    fun findByDeptNumberStartsWith(s: String): Iterable<DeptDto> {
        return this.systemDeptRepository.findByDeptNumberStartsWith(s)
    }
    @Transactional(readOnly = true)
    fun findAll(): Iterable<SystemDept> {
        return this.systemDeptRepository.findAll()
    }
}
