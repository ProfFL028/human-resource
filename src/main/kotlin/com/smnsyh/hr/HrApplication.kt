package com.smnsyh.hr

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@EnableWebMvc
@EnableConfigurationProperties
@SpringBootApplication
class HrApplication {

}

fun main(args: Array<String>) {
    runApplication<HrApplication>(*args)
}
