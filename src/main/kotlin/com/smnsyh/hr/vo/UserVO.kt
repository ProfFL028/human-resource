package com.smnsyh.hr.vo

data class UserVO(
        var tellerNumber: String = "",
        var name: String? = "",
        var telephone: String? = "",
        var autoLoginIp: String? = "",
        var sortNumber: Int = 999,
        var dept: DeptVO? = null,
        var position: PositionVO? = null
) {

}
