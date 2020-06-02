package com.smnsyh.hr.config

import org.modelmapper.ModelMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class EntityConfig  {

    @Bean
    fun modelMapper(): ModelMapper {
        return ModelMapper()
    }
}
