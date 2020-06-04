package com.smnsyh.hr.repository

import com.smnsyh.hr.dto.DeptDto
import com.smnsyh.hr.entity.SystemDept
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
interface SystemDeptRepository : CrudRepository<SystemDept, Short> {
    fun findAllByOrderByParent(): List<SystemDept>

    fun findByDeptNumberStartsWith(s: String): Iterable<DeptDto>

}
