package com.smnsyh.hr.vo

enum class ResultCode(
        var code: Int,
        var message: String
) {
    SUCCESS(1000, "操作成功"),

    FAILED(1001, "响应失败"),

    VALIDATE_FAILED(1002, "参数校验失败"),

    ERROR(5000, "未知错误");
}
