package com.smnsyh.hr.entity

import org.hibernate.annotations.Sort
import javax.persistence.*

@Entity(name = "system_position")
data class SystemPosition(
        @Id
        @GeneratedValue
        val id: Short = 0,

        @Column
        val name: String = "",

        @Column(name="sort_number")
        val sortNumber: Int = 999,

        @Column
        val status: Boolean = true,

        @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true, mappedBy = "position")
        val users: List<SystemUser> = ArrayList()
) {
}
