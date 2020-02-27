package com.smnsyh.hr.repository

import com.smnsyh.hr.entity.SystemRole
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
interface SystemRoleRepository: CrudRepository<SystemRole, Short>
