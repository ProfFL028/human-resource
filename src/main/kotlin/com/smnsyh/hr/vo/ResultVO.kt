package com.smnsyh.hr.vo

class ResultVO<T>(
        var data: T,
        var code: Int = 200,
        var message: String = "success"
) {
    constructor(resultCode: ResultCode, data: T): this(data) {
        this.code = resultCode.code
        this.message = resultCode.message
    }
}
