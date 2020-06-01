package com.smnsyh.hr.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import org.hibernate.annotations.Cascade
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import org.hibernate.annotations.SortNatural
import java.util.*
import javax.persistence.*
import com.smnsyh.hr.entity.SystemPermission as SystemPermission

@Entity
@PrimaryKeyJoinColumn(name="menu_id")
class SystemMenuPermission (
        @Column
        var url: String = "",

        @Column
        var icon: String = "",

        @ManyToOne(fetch = FetchType.LAZY, optional = true)
        @JoinColumn(name = "parent_id", nullable = true)
        var parent: SystemMenuPermission? = null,

        @JsonBackReference
        @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent", orphanRemoval = false)
        var children: MutableSet<SystemMenuPermission> = TreeSet<SystemMenuPermission>()

) : SystemPermission() {

    fun addChild(menu: SystemMenuPermission) {
        menu.parent = this
        this.children.add(menu)
    }

    fun removeChild(menu: SystemMenuPermission) {
        menu.parent = null
        this.children.remove(menu)
    }

    fun removeChildren() {
        for (menu in this.children) {
            menu.parent = null
            menu.removeChildren()
        }
        this.children.clear()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as SystemMenuPermission

        if (url != other.url) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + url.hashCode()
        return result
    }

    override fun toString(): String {
        return "SystemPermission='${super.toString()}',SystemMenuPermission(url='$url', icon='$icon')"
    }


}
