package com.smnsyh.hr.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity(name = "system_position")
data class SystemPosition(
        @Id
        val id: Short,

        @Column
        val name: String,

        @Column
        val sort_number: Int = 999,

        @Column
        val status: Boolean = true
) {
    constructor() : this(0, "", 999, true)
}
