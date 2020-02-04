package com.smnsyh.hr.dojo

import java.util.*

data class MenuTree(
        var name: String = "",
        var url: String = "",
        var icon: String = "",
        var sortNumber: Int = 999
        ) : Comparable<MenuTree> {
    var children: SortedSet<MenuTree>? = null

    override fun compareTo(other: MenuTree): Int {
        return this.sortNumber.compareTo(other.sortNumber)
    }
}
