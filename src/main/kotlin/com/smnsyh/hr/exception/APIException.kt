package com.smnsyh.hr.exception

import java.lang.RuntimeException

class APIException(
        var code: Int = 1001,
        message: String = "接口错误"
) : RuntimeException(message) {
}
