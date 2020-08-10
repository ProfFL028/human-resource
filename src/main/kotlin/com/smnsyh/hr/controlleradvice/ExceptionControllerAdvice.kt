package com.smnsyh.hr.controlleradvice

import com.smnsyh.hr.vo.ResultVO
import com.smnsyh.hr.exception.APIException
import com.smnsyh.hr.vo.ResultCode
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun methodArgumentNotValidExceptionHandler(e: MethodArgumentNotValidException):  ResultVO<String?>  {
        var objError = e.bindingResult.allErrors[0]
        return ResultVO(ResultCode.VALIDATE_FAILED, objError.defaultMessage)
    }

    @ExceptionHandler(APIException::class)
    fun apiExceptionHandler(e: APIException): ResultVO<String?> {
        return ResultVO(ResultCode.FAILED, e.message)
    }
}
