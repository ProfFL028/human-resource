package com.smnsyh.hr.repository

import com.smnsyh.hr.entity.SystemMenu
import com.smnsyh.hr.entity.SystemUser
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
interface SystemMenuRepository : CrudRepository<SystemMenu, Int>, SystemMenuRepositoryCustom

interface SystemMenuRepositoryCustom {
    fun findAvailableMenusTo(systemUser: SystemUser): List<SystemMenu>
}

class SystemMenuRepositoryImpl : SystemMenuRepositoryCustom {
    @PersistenceContext
    private lateinit var entityManager: EntityManager

    override fun findAvailableMenusTo(systemUser: SystemUser): List<SystemMenu> {
        return listOf<SystemMenu>()
    }

}
