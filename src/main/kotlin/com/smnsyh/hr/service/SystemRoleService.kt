package com.smnsyh.hr.service

import com.smnsyh.hr.dto.RoleDto
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
    fun findAll(): List<RoleDto> {
        var systemRoles = systemRoleRepository.findAll()

        return systemRoles.map { role -> modelMapper.map(role, RoleDto::class.java)}.toList()
    }

    @Transactional
    fun toggleStatus(id: Short) {
        systemRoleRepository.toggleStatus(id)
    }

}
