package com.smnsyh.hr.repository

import com.smnsyh.hr.entity.SystemRole
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
interface SystemRoleRepository: CrudRepository<SystemRole, Short> {
    @Modifying
    @Query("update system_role r set status=(status=false) where r.id=:id", nativeQuery = true)
    fun toggleStatus(id: Short)
}
