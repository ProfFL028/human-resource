package com.smnsyh.hr.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonFormat
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import org.hibernate.annotations.SortNatural
import java.time.LocalDate
import java.util.*
import javax.persistence.*
import kotlin.collections.ArrayList

@Entity(name = "system_dept")
data class SystemDept(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Short? = null,

        @Column(name = "full_name")
        var fullName: String? = null,

        @Column(name = "short_name")
        var shortName: String? = null,

        @Column(name = "dept_number")
        var deptNumber: String? = null,

        @Column(name = "sort_number")
        var sortNumber: Int = 999,

        @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08")
        @Column(name = "begin_date")
        var beginDate: LocalDate = LocalDate.now(),

        @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08")
        @Column(name = "end_date")
        var endDate: LocalDate = LocalDate.MAX,

        @OneToMany(cascade = [CascadeType.ALL], mappedBy = "dept", orphanRemoval = false)
        var users: MutableList<SystemUser> = ArrayList(),

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "parent_id", nullable = true)
        @Fetch(FetchMode.SELECT)
        var parent: SystemDept? = null,

        @JsonBackReference
        @OneToMany(mappedBy = "parent", cascade = [CascadeType.ALL], orphanRemoval = true)
        var children: MutableSet<SystemDept> = TreeSet(),

        @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        @JoinTable(
                name = "system_dept_position",
                joinColumns = [JoinColumn(name = "dept_id")],
                inverseJoinColumns = [JoinColumn(name = "position_id")]
        )
        var positions: MutableSet<SystemPosition> = TreeSet(),

        @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        @JoinTable(
                name = "system_dept_role",
                joinColumns = [JoinColumn(name = "dept_id")],
                inverseJoinColumns = [JoinColumn(name = "role_id")]
        )
        var roles: MutableSet<SystemRole> = TreeSet<SystemRole>(),

        @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        @JoinTable(
                name = "system_dept_permission",
                joinColumns = [JoinColumn(name = "dept_id")],
                inverseJoinColumns = [JoinColumn(name = "permission_id")]
        )
        var permissions: MutableSet<SystemPermission> = TreeSet()

) : Comparable<SystemDept> {

    fun addRole(role: SystemRole) {
        this.roles.add(role)
        role.depts.add(this)
    }

    fun removeRole(role: SystemRole) {
        this.roles.remove(role)
        role.depts.remove(this)
    }

    fun removeRoles() {
        for (role in this.roles) {
            role.depts.remove(this)
        }
        this.roles.clear()
    }

    fun addPosition(position: SystemPosition) {
        position.depts.add(this)
        this.positions.add(position)
    }

    fun removePosition(position: SystemPosition) {
        position.depts.remove(this)
        this.positions.remove(position)
    }

    fun removePositions() {
        for (position in this.positions) {
            position.depts.remove(this)
        }
        this.positions.clear()
    }

    fun addChild(dept: SystemDept) {
        dept.parent = this
        this.children.add(dept)
    }

    fun removeChild(dept: SystemDept) {
        dept.parent = null
        this.children.remove(dept)
    }

    fun removeChildren() {
        for (dept in this.children) {
            dept.parent = null
            dept.removeChildren()
        }
        this.children.clear()
    }

    fun addUser(user: SystemUser) {
        user.dept = this
        this.users.add(user)
    }

    fun removeUser(user: SystemUser) {
        user.dept = null
        this.users.remove(user)
    }

    fun removeUsers() {
        for (user in this.users) {
            user.dept = null
        }
        this.users.clear()
    }

    fun addPermission(permission: SystemPermission) {
        this.permissions.add(permission)
        permission.depts.add(this)
    }

    fun removePermission(permission: SystemPermission) {
        this.permissions.remove(permission)
        permission.depts.remove(this)
    }

    fun removePermissions() {
        for (permission in permissions) {
            permission.depts.remove(this)
        }
        this.permissions.clear()
    }

    fun removeAll() {
        this.removeChildren()
        this.removeUsers()
        this.removePositions()
        this.removePermissions()
    }



    override fun compareTo(other: SystemDept): Int {
        var parentCompare = parent?.compareTo(other.parent ?: SystemDept()) ?: 0
        if (parentCompare == 0) {
            return sortNumber.compareTo(other.sortNumber)
        }
        return parentCompare
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SystemDept

        if (id != other.id) return false
        if (fullName != other.fullName) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.toInt() ?: 0
        result = 31 * result + (fullName?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "SystemDept(id=$id, fullName=$fullName, shortName=$shortName, deptNumber=$deptNumber, sortNumber=$sortNumber, beginDate=$beginDate, endDate=$endDate)"
    }


}
