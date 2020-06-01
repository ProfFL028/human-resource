package com.smnsyh.hr.dojo

import com.fasterxml.jackson.annotation.JsonFormat
import com.smnsyh.hr.entity.SystemDept
import java.time.LocalDate
import java.util.*

class SystemDeptTreeNode() {
    var data = SystemDeptData()
    var expanded = false
    lateinit var children: MutableList<SystemDeptTreeNode>

    constructor(systemDept: SystemDept, expanded: Boolean = false) : this() {
        data = SystemDeptData(systemDept)
        this.expanded = expanded
        children = ArrayList()
    }

    constructor(systemDepts: Iterable<SystemDept>) : this() {
        for (systemDept in systemDepts) {
            data = SystemDeptData(systemDept)
            children = ArrayList()
            children.add(SystemDeptTreeNode(systemDept))
        }

    }

    fun addNodeIfMyChild(systemDept: SystemDept) {
        if (systemDept.parent != null) {
            if (systemDept.parent!!.id == this.data.id) {
                this.children.add(SystemDeptTreeNode(systemDept, false))
            } else {
                for (child in this.children) {
                    child.addNodeIfMyChild(systemDept)
                }
            }
        }
    }
}


class SystemDeptData(
        var id: Short = 0,
        var deptNumber: String = "",
        var fullName: String = "",
        var shortName: String = "",
        var sortNumber: Int = 999,
        @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08")
        var beginDate: LocalDate = LocalDate.now(),
        @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08")
        var endDate: LocalDate = LocalDate.MAX,
        var parent: SystemDeptData? = null) {

    constructor(systemDept: SystemDept, copyParent: Boolean = true) : this() {
        this.id = systemDept.id ?: 0
        this.deptNumber = systemDept.deptNumber ?: ""
        this.fullName = systemDept.fullName ?: ""
        this.shortName = systemDept.shortName ?: ""
        this.sortNumber = systemDept.sortNumber
        this.beginDate = systemDept.beginDate
        this.endDate = systemDept.endDate
        if (copyParent && systemDept.parent != null) {
            this.parent = SystemDeptData(systemDept.parent!!, false)
        }
    }
}
