package com.smnsyh.hr.service

import com.smnsyh.hr.dto.RoleDto
import com.smnsyh.hr.entity.SystemRole
import com.smnsyh.hr.repository.SystemRoleRepository
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Service

@Service
class SystemRoleService(
        private val systemRoleRepository: SystemRoleRepository,
        private val modelMapper: ModelMapper
) {

    fun findAll(): List<RoleDto> {
        var systemRoles = systemRoleRepository.findAll()

        return systemRoles.map { role -> modelMapper.map(role, RoleDto::class.java)}.toList()
    }

}
