package com.smnsyh.hr.entity

import com.smnsyh.hr.dto.RoleDto
import org.hibernate.annotations.SortNatural
import java.util.*
import javax.persistence.*

@Entity(name = "system_role")
data class SystemRole(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Short? = null,

        @Column(name = "name")
        var name: String? = null,

        @Column(name = "sort_number")
        var sortNumber: Int = 999,

        @Column(name = "status")
        var status: Boolean = true,

        @ManyToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE], fetch = FetchType.LAZY)
        @JoinTable(
                name = "system_role_permission",
                joinColumns = [JoinColumn(name = "role_id")],
                inverseJoinColumns = [JoinColumn(name = "permission_id")]
        )
        var permissions: MutableSet<SystemPermission> = TreeSet(),

        @ManyToMany(mappedBy = "roles")
        var depts: MutableSet<SystemDept> = TreeSet(),

        @ManyToMany(mappedBy = "roles")
        var positions: MutableSet<SystemPosition> = TreeSet(),

        @ManyToMany(mappedBy = "roles")
        var users: MutableSet<SystemUser> = TreeSet()

) : Comparable<SystemRole> {

    fun convertToRoleDto(): RoleDto {
        return RoleDto(id=this.id, name=this.name, sortNumber = this.sortNumber, status = this.status)
    }

    fun addPermission(permission: SystemPermission) {
        permission.roles.add(this)
        this.permissions.add(permission)
    }

    fun removePermission(permission: SystemPermission) {
        permission.roles.remove(this)
        this.permissions.remove(permission)
    }

    fun removePermissions() {
        for (permission in permissions) {
            permission.roles.remove(this)
        }
        this.permissions.clear()
    }


    override fun compareTo(other: SystemRole): Int {
        return sortNumber.compareTo(other.sortNumber)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SystemRole

        if (id != other.id) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.toInt() ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "SystemRole(id=$id, name=$name, sortNumber=$sortNumber, status=$status)"
    }
}
