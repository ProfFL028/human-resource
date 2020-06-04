package com.smnsyh.hr.entity

import java.io.Serializable
import java.util.*
import javax.persistence.*
import kotlin.collections.ArrayList

@Entity
data class SystemPosition(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Short? = null,

        @Column
        var name: String? = null,

        @Column(name = "sort_number")
        var sortNumber: Int = 999,

        @Column
        var status: Boolean = true,

        @OneToMany(orphanRemoval = false, mappedBy = "position")
        var users: MutableList<SystemUser> = ArrayList(),

        @ManyToMany(mappedBy = "positions")
        var depts: MutableSet<SystemDept> = TreeSet(),

        @ManyToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE],fetch = FetchType.LAZY)
        @JoinTable(
                name = "system_position_role",
                joinColumns = [JoinColumn(name = "position_id")],
                inverseJoinColumns = [JoinColumn(name = "role_id")]
        )
        var roles: MutableSet<SystemRole> = TreeSet(),

        @ManyToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE],fetch = FetchType.LAZY)
        @JoinTable(
                name = "system_position_permission",
                joinColumns = [JoinColumn(name = "position_id")],
                inverseJoinColumns = [JoinColumn(name = "permission_id")]
        )
        var permissions: MutableSet<SystemPermission> = TreeSet()
) : Serializable, Comparable<SystemPosition> {

    fun addRole(role: SystemRole) {
        this.roles.add(role)
        role.positions.add(this)
    }

    fun removeRole(role: SystemRole) {
        this.roles.remove(role)
        role.positions.remove(this)
    }

    fun removeRoles() {
        for (role in this.roles) {
            role.positions.remove(this)
        }
        this.roles.clear()
    }

    fun addPermission(permission: SystemPermission) {
        permission.positions.add(this)
        this.permissions.add(permission)
    }

    fun removePermission(permission: SystemPermission) {
        permission.positions.remove(this)
        this.permissions.remove(permission)
    }

    fun removePermissions() {
        for (permission in permissions) {
            permission.positions.remove(this)
        }
        this.permissions.clear()
    }


    fun addUser(user: SystemUser) {
        user.position = this
        this.users.add(user)
    }

    fun removeUser(user: SystemUser) {
        this.users.remove(user)
        user.position = null
    }

    fun removeUsers() {
        for (user in this.users) {
            user.position = null
        }
        this.users.clear()
    }

    override fun compareTo(other: SystemPosition): Int {
        return sortNumber.compareTo(other.sortNumber)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SystemPosition

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.toInt() ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "SystemPosition(id=$id, name=$name, sortNumber=$sortNumber, status=$status)"
    }


}
