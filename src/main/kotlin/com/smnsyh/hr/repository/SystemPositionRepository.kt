package com.smnsyh.hr.repository

import com.smnsyh.hr.entity.SystemPosition
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
interface SystemPositionRepository : CrudRepository<SystemPosition, Short> {
    fun findAllByOrderBySortNumber(): List<SystemPosition>

    @Query("select p.* from system_position p where not exists(select 1 from system_dept_positions x where p.id=x.position_id and x.dept_id=:deptId)", nativeQuery = true)
    fun findAvailablePositions(deptId: Short): List<SystemPosition>

    @Query("select p.* from system_position p where exists(select 1 from system_dept_positions x where p.id=x.position_id and x.dept_id=:deptId)", nativeQuery = true)
    fun findOwnedPositions(deptId: Short): List<SystemPosition>
}
