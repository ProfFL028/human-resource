package com.smnsyh.hr.repository

import com.smnsyh.hr.entity.SystemPosition
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
interface SystemPositionRepository : JpaRepository<SystemPosition, Short> {
    fun findAllByOrderBySortNumber(): List<SystemPosition>

    @Query("select p.* from system_position p where p.status=true and not exists(select 1 from system_dept_positions x where p.id=x.position_id and x.dept_id=:deptId)", nativeQuery = true)
    fun findAvailablePositions(deptId: Short): List<SystemPosition>

    @Query("select p.* from system_position p where p.status=true and exists(select 1 from system_dept_positions x where p.id=x.position_id and x.dept_id=:deptId)", nativeQuery = true)
    fun findOwnedPositions(deptId: Short): List<SystemPosition>

    @Modifying
    @Query("update system_position set status=(status = false) where id=:id", nativeQuery = true)
    fun toggleStatus(id: Short)

    @Modifying
    @Query("update system_position set name=:#{#systemPosition.name}, sortNumber=:#{#systemPosition.sortNumber}, status=:#{#systemPosition.status} where id=:#{#systemPosition.id}")
    fun updateBasicProperties(@Param("systemPosition") systemPosition: SystemPosition)

}
