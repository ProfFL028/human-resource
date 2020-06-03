package com.smnsyh.hr.dto

data class PositionDto(
        var id: Short? = null,
        var name: String? = null,
        var sortNumber: Int = 999,
        var status: Boolean = true
) {}
