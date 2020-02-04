package com.smnsyh.hr.dojo

import com.smnsyh.hr.entity.SystemMenu
import com.smnsyh.hr.entity.SystemUser
import java.util.*
import kotlin.collections.ArrayList

data class UserDetail(
        var tellerNumber: String = "",
        var name: String = "",
        var telephone: String = "",
        var deptName: String = "",
        var positionName: String = "",
        var autoLoginIp: String = "",
        var menus: SortedSet<MenuTree>? = null
) {

    companion object {
        fun fromSystemUser(systemUser: SystemUser?): UserDetail {
            var userDetail = UserDetail()
            if (systemUser != null) {
                userDetail.tellerNumber = systemUser.tellerNumber
                userDetail.name = systemUser.name
                userDetail.telephone = systemUser.telephone
                userDetail.deptName = systemUser.dept?.name ?: ""
                userDetail.autoLoginIp = systemUser.autoLoginIp
                userDetail.positionName = systemUser.position?.name ?: ""
            }

            return userDetail
        }
    }

    fun setAvailableMenus(availableMenus: List<SystemMenu>) {

    }
}
