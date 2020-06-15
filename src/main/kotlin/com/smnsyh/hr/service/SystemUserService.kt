package com.smnsyh.hr.service

import com.smnsyh.hr.dto.UserDto
import com.smnsyh.hr.entity.SystemUser
import com.smnsyh.hr.repository.SystemDeptRepository
import com.smnsyh.hr.repository.SystemPositionRepository
import com.smnsyh.hr.repository.SystemUserRepository
import org.modelmapper.ModelMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import javax.persistence.EntityManager

@Service
class SystemUserService(
        val systemUserRepository: SystemUserRepository,
        val systemDeptRepository: SystemDeptRepository,
        val systemPositionRepository: SystemPositionRepository,
        val entityManager: EntityManager,
        val modelMapper: ModelMapper
) {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(SystemUserService::class.java)
    }

    fun findUsers(page: Int, size: Int): Page<UserDto> {
        return this.systemUserRepository.findAll(PageRequest.of(page, size, Sort.by("sortNumber")))
    }

    fun save(userDto: UserDto) {
        var systemUser = this.modelMapper.map(userDto, SystemUser::class.java)
        var systemDept = this.systemDeptRepository.findByIdOrNull(userDto.dept?.id ?: 0)
        var systemPosition = this.systemPositionRepository.findByIdOrNull(userDto.position?.id ?: 0)
        systemUser.dept = systemDept
        systemUser.position = systemPosition

        if (StringUtils.isEmpty(userDto)) {
            this.systemUserRepository.save(systemUser)
        } else {
            var updateQuery = this.entityManager.createQuery("update SystemUser set name=:name, password=:password, " +
                    "telephone=:telephone, autoLoginIp=:autoLoginIp, sortNumber=sortNumber, dept=:dept, " +
                    "position=:position  where tellerNumber=:tellerNumber")
            updateQuery.setParameter("name", userDto.name)
            updateQuery.setParameter("password", userDto.password)
            updateQuery.setParameter("telephone", userDto.telephone)
            updateQuery.setParameter("autoLoginIp", userDto.autoLoginIp)
            updateQuery.setParameter("sortNumber", userDto.sortNumber)
            updateQuery.setParameter("dept", userDto.dept)
            updateQuery.setParameter("position", userDto.position)
            updateQuery.setParameter("tellerNumber", userDto.tellerNumber)

            updateQuery.executeUpdate()
        }
    }
}

