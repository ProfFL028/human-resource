package com.smnsyh.hr.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import org.hibernate.annotations.Cascade
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import org.hibernate.annotations.SortNatural
import java.util.*
import javax.persistence.*

@Entity
data class SystemMenu(
        @Id
        @GeneratedValue
        val id: Int,

        @Column
        val name: String,

        @Column
        val url: String,

        @Column
        val icon: String,

        @Column(name = "sort_number")
        val sortNumber: Int = 999,

        @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL], optional = true)
        @JoinColumn(name = "parent_id", nullable = true)
        @Fetch(FetchMode.SELECT)
        @Cascade(org.hibernate.annotations.CascadeType.ALL, org.hibernate.annotations.CascadeType.SAVE_UPDATE)
        val parent: SystemMenu?,

        @JsonBackReference
        @OneToMany(fetch = FetchType.EAGER, mappedBy = "parent")
        @Fetch(FetchMode.SUBSELECT)
        @SortNatural
        @OrderBy("sortNumber ASC")
        val children: SortedSet<SystemMenu>? = TreeSet<SystemMenu>()

) : Comparable<SystemMenu> {

    constructor() : this(0, "", "", "", 999, null, null)

    override fun compareTo(other: SystemMenu): Int {
        return sortNumber.compareTo(other.sortNumber)
    }

}
