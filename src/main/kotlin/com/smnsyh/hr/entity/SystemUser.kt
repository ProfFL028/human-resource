package com.smnsyh.hr.entity

import org.hibernate.annotations.SortNatural
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "system_user")
data class SystemUser(
        @Id
        @Column(name = "teller_number")
        val tellerNumber: String = "",

        @Column
        val name: String = "",

        @Column
        val password: String = "",

        @Column
        val telephone: String = "",

        @Column(name = "auto_login_ip")
        val autoLoginIp: String = "",

        @ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="dept_id")
        val dept: SystemDept? = null,

        @ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="position_id")
        val position: SystemPosition? = null,

        @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        @JoinTable(
                name = "system_user_roles",
                joinColumns = [JoinColumn(name = "teller_number")],
                inverseJoinColumns = [JoinColumn(name = "role_id")]
        )
        @SortNatural
        @OrderBy("sortNumber ASC")
        val roles: Set<SystemRole>? = TreeSet<SystemRole>()
)
