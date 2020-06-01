package com.smnsyh.hr.entity

import com.sun.source.util.Trees
import org.hibernate.annotations.SortNatural
import java.io.Serializable
import java.security.Permissions
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "system_user")
data class SystemUser(
        @Id
        @Column(name = "teller_number")
        var tellerNumber: String? = null,

        @Column
        var name: String? = null,

        @Column
        var password: String? = null,

        @Column
        var telephone: String? = null,

        @Column(name = "auto_login_ip")
        var autoLoginIp: String? = null,

        @Column(name = "sort_number")
        var sortNumber: Int = 999,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "dept_id")
        var dept: SystemDept? = null,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "position_id")
        var position: SystemPosition? = null,

        @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        @JoinTable(
                name = "system_user_role",
                joinColumns = [JoinColumn(name = "user_id")],
                inverseJoinColumns = [JoinColumn(name = "role_id")]
        )
        var roles: MutableSet<SystemRole> = TreeSet<SystemRole>(),

        @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        @JoinTable(
                name = "system_user_permission",
                joinColumns = [JoinColumn(name = "user_id")],
                inverseJoinColumns = [JoinColumn(name = "permission_id")]
        )
        var permissions: MutableSet<SystemPermission> = TreeSet()
) : Serializable, Comparable<SystemUser> {

    fun addPermission(permission: SystemPermission) {
        permission.users.add(this)
        this.permissions.add(permission)
    }

    fun removePermission(permission: SystemPermission) {
        permission.users.remove(this)
        this.permissions.remove(permission)
    }

    fun removePermissions() {
        for (permission in permissions) {
            permission.users.remove(this)
        }
        this.permissions.clear()
    }

    fun addRole(role: SystemRole) {
        this.roles.add(role)
        role.users.add(this)
    }

    fun removeRole(role: SystemRole) {
        this.roles.remove(role)
        role.users.remove(this)
    }

    fun removeRoles() {
        for (role in this.roles) {
            role.users.remove(this)
        }
        this.roles.clear()
    }

    override fun compareTo(other: SystemUser): Int {
        var positionCompare = position?.compareTo(other.position?: SystemPosition()) ?: 0
        if( positionCompare == 0) {
            return sortNumber.compareTo(other.sortNumber)
        }
        return positionCompare
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SystemUser

        if (tellerNumber != other.tellerNumber) return false
        if (name != other.name) return false
        if (telephone != other.telephone) return false

        return true
    }

    override fun hashCode(): Int {
        var result = tellerNumber?.hashCode() ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (telephone?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "SystemUser(tellerNumber=$tellerNumber, name=$name, password=$password, telephone=$telephone, autoLoginIp=$autoLoginIp, sortNumber=$sortNumber)"
    }

}
