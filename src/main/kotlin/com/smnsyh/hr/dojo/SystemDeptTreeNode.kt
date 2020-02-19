package com.smnsyh.hr.dojo

import com.smnsyh.hr.entity.SystemDept
import java.util.*

class SystemDeptTreeNode() {
    lateinit var data: SystemDeptTreeData
    var expanded = false
    lateinit var children: MutableList<SystemDeptTreeNode>

    constructor(systemDept: SystemDept) : this() {
        data = SystemDeptTreeData(systemDept.deptNumber, systemDept.fullName, systemDept.shortName, systemDept.sortNumber)
        children = ArrayList()
    }

    constructor(systemDepts: Iterable<SystemDept>) : this() {
        for (systemDept in systemDepts) {
            data = SystemDeptTreeData(systemDept.deptNumber, systemDept.fullName, systemDept.shortName, systemDept.sortNumber)

            children = ArrayList()
            children.add(SystemDeptTreeNode(systemDept))
        }

    }
}


class SystemDeptTreeData(var deptNumber: String, var fullName: String, var shortName: String, var sortNumber: Int)
