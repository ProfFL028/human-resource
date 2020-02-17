package com.smnsyh.hr.controller

import com.smnsyh.hr.entity.SystemPosition
import com.smnsyh.hr.repository.SystemPositionRepository
import org.springframework.web.bind.annotation.*

@RestController
class SystemPositionController(val systemPositionRepository: SystemPositionRepository) {

    @GetMapping(ApiController.SYSTEM_POSITION_URL)
    fun findAll(): List<SystemPosition> {
        return this.systemPositionRepository.findAllByOrderBySortNumber()
    }

    @PostMapping(ApiController.SYSTEM_POSITION_URL)
    fun save(@RequestBody systemPosition: SystemPosition): SystemPosition {
        return this.systemPositionRepository.save(systemPosition)
    }

    @DeleteMapping(ApiController.SYSTEM_POSITION_URL + "/{id}")
    fun delete(@PathVariable id: Short) {
        this.systemPositionRepository.deleteById(id)
    }
}
