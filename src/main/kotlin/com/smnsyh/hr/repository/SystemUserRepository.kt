package com.smnsyh.hr.repository

import com.smnsyh.hr.dto.UserDto
import com.smnsyh.hr.entity.SystemUser
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
@Transactional
interface SystemUserRepository : CrudRepository<SystemUser, String>, SystemUserRepositoryCustom {
    @Modifying
    @Query("update SystemUser set password=:password where username=:username")
    fun updatePassword(@Param("username") username: String, @Param("password") password: String): Int

    fun findAll(pageable: Pageable): Page<UserDto>
}

interface SystemUserRepositoryCustom {
    fun getPasswordBy(tellerNumber: String): String
}

@Repository
@Transactional
class SystemUserRepositoryImpl : SystemUserRepositoryCustom {
    @PersistenceContext
    private lateinit var entityManager: EntityManager

    override fun getPasswordBy(tellerNumber: String): String {
        val query = entityManager.createNativeQuery("select password from system_user where teller_number=:tellerNumber")
        query.setParameter("tellerNumber", tellerNumber)
        return query.resultStream.findFirst().orElse("").toString()
    }

}
