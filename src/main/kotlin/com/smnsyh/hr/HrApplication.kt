package com.smnsyh.hr

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import java.util.*
import javax.annotation.PostConstruct

@EnableWebMvc
@EnableConfigurationProperties
@SpringBootApplication
class HrApplication {
    @PostConstruct
    fun started() {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
    }

}

fun main(args: Array<String>) {
    runApplication<HrApplication>(*args)
}
