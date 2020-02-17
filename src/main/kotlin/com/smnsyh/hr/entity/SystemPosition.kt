package com.smnsyh.hr.entity

import org.hibernate.annotations.Sort
import javax.persistence.*

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
