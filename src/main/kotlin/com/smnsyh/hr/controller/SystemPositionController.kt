package com.smnsyh.hr.controller

import com.smnsyh.hr.vo.PositionVO
import com.smnsyh.hr.service.SystemPositionService
import org.springframework.web.bind.annotation.*

@RestController
class SystemPositionController(
        private val systemPositionsService: SystemPositionService) {

    @GetMapping(ApiController.SYSTEM_POSITION_URL)
    fun findAll(): List<PositionVO> {
        return this.systemPositionsService.findAllByOrderBySortNumber()
    }

    @GetMapping(ApiController.SYSTEM_POSITION_URL + "/systemDept/available/{deptId}")
    fun findAvailablePositions(@PathVariable deptId: Short): List<PositionVO> {
        return this.systemPositionsService.findAvailablePositions(deptId)
    }

    @GetMapping(ApiController.SYSTEM_POSITION_URL + "/systemDept/owned/{deptId}")
    fun findUnavailablePositions(@PathVariable deptId: Short): List<PositionVO> {
        return this.systemPositionsService.findOwnedPositions(deptId)
    }

    @PostMapping(ApiController.SYSTEM_POSITION_URL)
    fun save(@RequestBody positionDto: PositionVO): PositionVO {
        return this.systemPositionsService.save(positionDto)
    }

    @PostMapping("${ApiController.SYSTEM_POSITION_URL}/status/{id}")
    fun toggleStatus(@PathVariable id: Short) {
        this.systemPositionsService.toggleStatus(id)
    }

    @DeleteMapping(ApiController.SYSTEM_POSITION_URL + "/{id}")
    fun delete(@PathVariable id: Short) {
        this.systemPositionsService.deleteById(id)
    }
}
