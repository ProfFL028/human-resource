package com.smnsyh.hr.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity(name = "system_role")
data class SystemRole(
        @Id
        @GeneratedValue
        val id: Short,

        @Column(name = "name")
        val name: String,

        @Column(name = "sort_number")
        val sortNumber: Int = 999,

        @Column(name = "status")
        val status: Boolean = true
) : Comparable<SystemRole> {
    constructor() : this(0, "", 999, true)

    override fun compareTo(other: SystemRole): Int {
        return sortNumber.compareTo(other.sortNumber)
    }
}
