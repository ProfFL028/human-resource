package com.smnsyh.hr.dojo

import com.smnsyh.hr.entity.SystemMenuPermission
import com.smnsyh.hr.entity.SystemUser
import java.util.*

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
                userDetail.tellerNumber = systemUser.tellerNumber ?: ""
                userDetail.name = systemUser.name ?: ""
                userDetail.telephone = systemUser.telephone ?: ""
                userDetail.deptName = systemUser.dept?.shortName ?: ""
                userDetail.autoLoginIp = systemUser.autoLoginIp ?: ""
                userDetail.positionName = systemUser.position?.name ?: ""
            }

            return userDetail
        }
    }

    fun setAvailableMenus(availableMenuPermissions: List<SystemMenuPermission>) {

    }
}
