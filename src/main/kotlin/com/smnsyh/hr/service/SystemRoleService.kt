package com.smnsyh.hr.service

import com.smnsyh.hr.vo.RoleVO
import com.smnsyh.hr.entity.SystemRole
import com.smnsyh.hr.repository.SystemRoleRepository
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SystemRoleService(
        private val systemRoleRepository: SystemRoleRepository,
        private val modelMapper: ModelMapper
) {

    @Transactional(readOnly=true)
    fun findAll(): List<RoleVO> {
        var systemRoles = systemRoleRepository.findAll()

        return systemRoles.map { role -> modelMapper.map(role, RoleVO::class.java)}.toList()
    }

    @Transactional
    fun toggleStatus(id: Short) {
        systemRoleRepository.toggleStatus(id)
    }

    fun save(systemRole: SystemRole): SystemRole {
        if (systemRole.id == null) {
            this.systemRoleRepository.save(systemRole)
        } else {
            this.systemRoleRepository.updateBasicProperties(systemRole)
        }
        return systemRole
    }

    fun deleteById(id: Short) {
        systemRoleRepository.deleteById(id)
    }

}
