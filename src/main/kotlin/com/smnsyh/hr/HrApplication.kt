package com.smnsyh.hr

import com.smnsyh.hr.entity.*
import com.smnsyh.hr.repository.SystemDeptRepository
import com.smnsyh.hr.repository.SystemPositionRepository
import com.smnsyh.hr.repository.SystemRoleRepository
import com.smnsyh.hr.repository.SystemUserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import java.util.*

@EnableWebMvc
@EnableConfigurationProperties
@SpringBootApplication
class HrApplication {

    @Autowired
    private lateinit var systemUserRepository: SystemUserRepository

    @Autowired
    private lateinit var systemDeptRepository: SystemDeptRepository

    @Autowired
    private lateinit var systemRoleRepository: SystemRoleRepository

    @Autowired
    private lateinit var systemPositionRepository: SystemPositionRepository


    @Bean
    fun init() = CommandLineRunner {
        val dept1 = SystemDept(1, "三门农商银行", "F71000", 1, null)
        val dept2 = SystemDept(2, "办公室", "F71001", 2, dept1)
        val dept3 = SystemDept(3, "科技信息部", "F71002", 3, dept1)
        val dept4 = SystemDept(4, "营业部", "F71003", 4, dept1)
        val dept5 = SystemDept(5, "小微中心", "F71004", 5, dept4)
        systemDeptRepository.save(dept1)
        systemDeptRepository.save(dept2)
        systemDeptRepository.save(dept3)
        systemDeptRepository.save(dept4)
        systemDeptRepository.save(dept5)

        val role1 = SystemRole(1, "ROLE_USER", 1)
        val role2 = SystemRole(2, "ROLE_MANAGER", 1)
        systemRoleRepository.save(role1)
        systemRoleRepository.save(role2)

        val position1 = SystemPosition(1, "董事长")
        val position2 = SystemPosition(2, "科员")
        systemPositionRepository.save(position1)
        systemPositionRepository.save(position2)

        val menu1 = SystemMenu(1, "系统管理", "#", "", 1, null, null)
        val menu2 = SystemMenu(2, "菜单管理", "/menu", "", 1, menu1, null)
        val menu3 = SystemMenu(3, "人员管理", "/user", "", 2, menu1, null)

        val deptTree = TreeSet<SystemRole>()
        deptTree.add(role1)
        deptTree.add(role2)
        val systemUser1 = SystemUser("9712152", "方笠",
                BCryptPasswordEncoder().encode("9712152@smls"), "18857632016", "127.0.0.1", dept3, position2, deptTree)

        systemUserRepository.save(systemUser1)
    }
}

fun main(args: Array<String>) {
    runApplication<HrApplication>(*args)
}
