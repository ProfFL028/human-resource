package com.smnsyh.hr.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity(name = "system_position")
data class SystemPosition(
        @Id
        @GeneratedValue
        val id: Short,

        @Column
        val name: String,

        @Column(name="sort_number")
        val sortNumber: Int = 999,

        @Column
        val status: Boolean = true
) {
    constructor() : this(0, "", 999, true)
}
