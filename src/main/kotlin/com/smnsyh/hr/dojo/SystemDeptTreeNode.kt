package com.smnsyh.hr.dojo

import com.smnsyh.hr.entity.SystemDept
import java.time.LocalDate
import java.util.*

class SystemDeptTreeNode() {
    var data = SystemDeptData()
    var expanded = false
    lateinit var children: MutableList<SystemDeptTreeNode>

    constructor(systemDept: SystemDept) : this() {
        data = SystemDeptData(systemDept)
        children = ArrayList()
    }

    constructor(systemDepts: Iterable<SystemDept>) : this() {
        for (systemDept in systemDepts) {
            data = SystemDeptData(systemDept)

            children = ArrayList()
            children.add(SystemDeptTreeNode(systemDept))
        }

    }
}


class SystemDeptData(
        var deptNumber: String = "",
        var fullName: String = "",
        var shortName: String = "",
        var sortNumber: Int = 999,
        var beginDate: LocalDate = LocalDate.now(),
        var endDate: LocalDate = LocalDate.MAX) {

    constructor(systemDept: SystemDept) : this() {
        this.deptNumber = systemDept.deptNumber
        this.fullName = systemDept.fullName
        this.shortName = systemDept.shortName
        this.sortNumber = systemDept.sortNumber
        this.beginDate = systemDept.beginDate
        this.endDate = systemDept.endDate
    }
}
