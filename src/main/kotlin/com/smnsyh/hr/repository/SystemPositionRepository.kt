package com.smnsyh.hr.repository

import com.smnsyh.hr.entity.SystemPosition
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
interface SystemPositionRepository : CrudRepository<SystemPosition, Short> {
    fun findAllByOrderBySortNumber(): List<SystemPosition>
}
