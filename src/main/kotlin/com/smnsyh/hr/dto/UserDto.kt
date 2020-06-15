package com.smnsyh.hr.dto

data class UserDto(
        var tellerNumber: String,
        var name: String? = "",
        var password: String? = "",
        var telephone: String? = "",
        var autoLoginIp: String? = "",
        var sortNumber: Int = 999,
        var dept: DeptDto? = null,
        var position: PositionDto? = null
) {

}
