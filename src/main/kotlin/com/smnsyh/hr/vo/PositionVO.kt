package com.smnsyh.hr.vo

data class PositionVO(
        var id: Short? = null,
        var name: String? = null,
        var sortNumber: Int = 999,
        var status: Boolean = true
) {}
