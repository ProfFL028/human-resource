package com.smnsyh.hr.service

import com.smnsyh.hr.vo.UserVO
import com.smnsyh.hr.entity.SystemUser
import com.smnsyh.hr.repository.SystemDeptRepository
import com.smnsyh.hr.repository.SystemPositionRepository
import com.smnsyh.hr.repository.SystemUserRepository
import org.modelmapper.ModelMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@Service
class SystemUserService(
        val systemUserRepository: SystemUserRepository,
        val systemDeptRepository: SystemDeptRepository,
        val systemPositionRepository: SystemPositionRepository,
        val passwordEncoder: BCryptPasswordEncoder,
        val entityManager: EntityManager,
        val modelMapper: ModelMapper
) {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(SystemUserService::class.java)
    }

    @Transactional(readOnly = true)
    fun findUsers(page: Int, size: Int): Page<UserVO> {
        var userDto = this.systemUserRepository.findAll(PageRequest.of(page, size, Sort.by("sortNumber")))

        return userDto.map { userDto -> modelMapper.map(userDto, UserVO::class.java) }
    }

    @Transactional
    fun save(userDto: UserVO) {
        var systemUser = this.modelMapper.map(userDto, SystemUser::class.java)

        if (!this.systemUserRepository.existsById(systemUser.tellerNumber ?: "")) {
            systemUser.password = passwordEncoder.encode("123456")
            var insertQuery = this.entityManager.createNativeQuery("insert into system_user(auto_login_ip, dept_id, " +
                    "name, password, position_id, sort_number, telephone, teller_number) values (?, ?, ?, ?, ?, ?, ?," +
                    " ?)")
            insertQuery.setParameter(1, systemUser.autoLoginIp)
            insertQuery.setParameter(2, systemUser.dept?.id)
            insertQuery.setParameter(3, systemUser.name)
            insertQuery.setParameter(4, systemUser.password)
            insertQuery.setParameter(5, systemUser.position?.id)
            insertQuery.setParameter(6, systemUser.sortNumber)
            insertQuery.setParameter(7, systemUser.telephone)
            insertQuery.setParameter(8, systemUser.tellerNumber)
            insertQuery.executeUpdate()
        } else {
            var updateQuery = this.entityManager.createQuery("update SystemUser set name=:name, password=:password, " +
                    "telephone=:telephone, autoLoginIp=:autoLoginIp, sortNumber=:sortNumber, dept=:dept, " +
                    "position=:position  where tellerNumber=:tellerNumber")
            updateQuery.setParameter("name", systemUser.name)
            updateQuery.setParameter("password", systemUser.password)
            updateQuery.setParameter("telephone", systemUser.telephone)
            updateQuery.setParameter("autoLoginIp", systemUser.autoLoginIp)
            updateQuery.setParameter("sortNumber", systemUser.sortNumber)
            updateQuery.setParameter("dept", systemUser.dept)
            updateQuery.setParameter("position", systemUser.position)
            updateQuery.setParameter("tellerNumber", systemUser.tellerNumber)

            updateQuery.executeUpdate()
        }
    }

}

