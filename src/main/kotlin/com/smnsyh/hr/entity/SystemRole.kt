package com.smnsyh.hr.entity

import com.smnsyh.hr.vo.RoleVO
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
data class SystemRole(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Short? = null,

        @NotNull(message="角色名不能为空")
        @Size(min=2, message = "角色名长度不能少于2位")
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

    fun convertToRoleDto(): RoleVO {
        return RoleVO(id=this.id, name=this.name, sortNumber = this.sortNumber, status = this.status)
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
