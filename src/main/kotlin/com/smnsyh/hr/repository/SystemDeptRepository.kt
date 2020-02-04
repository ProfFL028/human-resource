package com.smnsyh.hr.repository

import com.smnsyh.hr.entity.SystemDept
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface SystemDeptRepository : CrudRepository<SystemDept, Short>
