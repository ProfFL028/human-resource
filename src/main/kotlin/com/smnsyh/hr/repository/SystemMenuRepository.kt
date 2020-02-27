package com.smnsyh.hr.repository

import com.smnsyh.hr.entity.SystemMenu
import com.smnsyh.hr.entity.SystemUser
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
@Transactional
interface SystemMenuRepository : CrudRepository<SystemMenu, Int>, SystemMenuRepositoryCustom

interface SystemMenuRepositoryCustom {
    fun findAvailableMenusTo(systemUser: SystemUser): List<SystemMenu>
}

@Transactional
class SystemMenuRepositoryImpl : SystemMenuRepositoryCustom {
    @PersistenceContext
    private lateinit var entityManager: EntityManager

    override fun findAvailableMenusTo(systemUser: SystemUser): List<SystemMenu> {
        return listOf<SystemMenu>()
    }

}
