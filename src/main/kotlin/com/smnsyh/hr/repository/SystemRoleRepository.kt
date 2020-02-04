package com.smnsyh.hr.repository

import com.smnsyh.hr.entity.SystemRole
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface SystemRoleRepository: CrudRepository<SystemRole, Short>
