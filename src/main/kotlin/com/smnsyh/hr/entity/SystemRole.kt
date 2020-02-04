package com.smnsyh.hr.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity(name = "system_role")
data class SystemRole(
        @Id
        val id: Short,

        @Column(name = "name")
        val name: String,

        @Column(name = "sort_number")
        val sortNumber: Int = 999
) : Comparable<SystemRole> {
    constructor() : this(0, "", 999)

    override fun compareTo(other: SystemRole): Int {
        return sortNumber.compareTo(other.sortNumber)
    }
}
