package com.smnsyh.hr.repository

import com.smnsyh.hr.entity.SystemPosition
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface SystemPositionRepository : CrudRepository<SystemPosition, Short>
