package com.smnsyh.hr.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import org.hibernate.annotations.Cascade
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import org.hibernate.annotations.SortNatural
import java.util.*
import javax.persistence.*

@Entity(name = "system_dept")
data class SystemDept(
        @Id
        @GeneratedValue
        val id: Short,

        @Column(name = "full_name")
        val fullName: String,

        @Column(name = "short_name")
        val shortName: String,

        @Column(name = "dept_number")
        val deptNumber: String = "",

        @Column(name = "sort_number")
        val sortNumber: Int = 999,

        @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL], optional = true)
        @JoinColumn(name = "parent_id", nullable = true)
        @Fetch(FetchMode.SELECT)
        @Cascade(org.hibernate.annotations.CascadeType.ALL, org.hibernate.annotations.CascadeType.SAVE_UPDATE)
        val parent: SystemDept?,

        @JsonBackReference
        @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
        @Fetch(FetchMode.SUBSELECT)
        @SortNatural
        @OrderBy("sortNumber ASC")
        val children: Set<SystemDept> = TreeSet<SystemDept>()
) : Comparable<SystemDept> {
    constructor() : this(0, "", "", "", 999, null, TreeSet<SystemDept>())
    constructor(id: Short, fullName: String, shortName: String, deptNumber: String, sort: Int, parent: SystemDept?) : this(id, fullName, shortName, deptNumber, sort, parent, TreeSet<SystemDept>())

    override fun compareTo(other: SystemDept): Int {
        return sortNumber.compareTo(other.sortNumber)
    }
}
