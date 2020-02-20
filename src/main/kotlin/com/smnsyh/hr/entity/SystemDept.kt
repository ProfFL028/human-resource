package com.smnsyh.hr.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import org.hibernate.annotations.Cascade
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import org.hibernate.annotations.SortNatural
import java.time.LocalDate
import java.util.*
import javax.persistence.*

@Entity(name = "system_dept")
data class SystemDept(
        @Id
        @GeneratedValue
        val id: Short = 0,

        @Column(name = "full_name")
        val fullName: String = "",

        @Column(name = "short_name")
        val shortName: String = "",

        @Column(name = "dept_number")
        val deptNumber: String = "",

        @Column(name = "sort_number")
        val sortNumber: Int = 999,

        @Column(name="begin_date")
        val beginDate: LocalDate = LocalDate.now(),

        @Column(name="end_date")
        val endDate: LocalDate = LocalDate.MAX,

        @JsonBackReference
        @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL], optional = true)
        @JoinColumn(name = "parent_id", nullable = true)
        @Fetch(FetchMode.SELECT)
        @Cascade(org.hibernate.annotations.CascadeType.ALL, org.hibernate.annotations.CascadeType.SAVE_UPDATE)
        val parent: SystemDept? = null,

        @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
        @Fetch(FetchMode.SUBSELECT)
        @SortNatural
        @OrderBy("sortNumber ASC")
        val children: Set<SystemDept> = TreeSet()
) : Comparable<SystemDept> {
    override fun compareTo(other: SystemDept): Int {
        return sortNumber.compareTo(other.sortNumber)
    }
}
