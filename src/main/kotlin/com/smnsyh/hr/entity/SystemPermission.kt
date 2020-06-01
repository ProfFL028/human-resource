package com.smnsyh.hr.entity

import org.hibernate.annotations.SortNatural
import java.io.Serializable
import java.util.*
import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
open class SystemPermission(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        open var id: Long? = null,

        open var name: String? = null,

        @Column(name = "SORTED_NUMBER")
        open var sortedNumber: Int = 999,

        @ManyToMany(mappedBy = "permissions")
        open var users: MutableSet<SystemUser> = TreeSet(),

        @ManyToMany(mappedBy = "permissions")
        open var depts: MutableSet<SystemDept> = TreeSet(),

        @ManyToMany(mappedBy = "permissions")
        open var roles: MutableSet<SystemRole> = TreeSet(),

        @ManyToMany(mappedBy = "permissions")
        open var positions: MutableSet<SystemPosition> = TreeSet()
) : Serializable, Comparable<SystemPermission> {

    override fun compareTo(other: SystemPermission): Int {
        return sortedNumber.compareTo(other.sortedNumber)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SystemPermission

        if (id != other.id) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "SystemPermission(id=$id, name=$name)"
    }


}
