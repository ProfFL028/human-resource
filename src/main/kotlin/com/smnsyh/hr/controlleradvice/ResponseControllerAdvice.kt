package com.smnsyh.hr.controlleradvice

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.smnsyh.hr.exception.APIException
import com.smnsyh.hr.vo.ResultVO
import org.springframework.core.MethodParameter
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice

@RestControllerAdvice(basePackages = ["com.smnsyh.hr.controller"] )
class ResponseControllerAdvice : ResponseBodyAdvice<Any?> {
    override fun supports(returnType: MethodParameter, converterType: Class<out HttpMessageConverter<*>>): Boolean {
        return returnType.genericParameterType != ResultVO::class.java
    }

    override fun beforeBodyWrite(body: Any?, returnType: MethodParameter, selectedContentType: MediaType,
                                 selectedConverterType: Class<out HttpMessageConverter<*>>, request:
                                 ServerHttpRequest, response: ServerHttpResponse): Any {
        if (returnType.genericParameterType == String::class.java) {
            var objectMapper = ObjectMapper()
            try {
                return objectMapper.writeValueAsString(ResultVO(data = body))
            } catch (e: JsonProcessingException) {
                throw APIException(message = "返回String类型错误")
            }
        }
        return ResultVO(data = body)
    }
}
