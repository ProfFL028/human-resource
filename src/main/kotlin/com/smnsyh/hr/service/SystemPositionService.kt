package com.smnsyh.hr.service

import com.smnsyh.hr.dto.PositionDto
import com.smnsyh.hr.entity.SystemDept
import com.smnsyh.hr.entity.SystemPosition
import com.smnsyh.hr.repository.SystemDeptRepository
import com.smnsyh.hr.repository.SystemPositionRepository
import org.modelmapper.ModelMapper
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class SystemPositionService(
        private val systemPositionRepository: SystemPositionRepository,
        private val systemDeptRepository: SystemDeptRepository,
        private val modelMapper: ModelMapper
) {
    @Transactional(readOnly = true)
    fun findAllByOrderBySortNumber(): List<PositionDto> {
        var positions = this.systemPositionRepository.findAllByOrderBySortNumber()
        return positions.map { position -> modelMapper.map(position, PositionDto::class.java) }
    }

    @Transactional(readOnly = true)
    fun findAvailablePositions(deptId: Short): List<PositionDto> {
        var positions = this.systemPositionRepository.findAvailablePositions(deptId)
        return positions.map { position -> modelMapper.map(position, PositionDto::class.java) }
    }

    @Transactional(readOnly = true)
    fun findOwnedPositions(deptId: Short): List<PositionDto> {
        var dept: SystemDept? = this.systemDeptRepository.findById(deptId).orElse(null)
        var positions: MutableSet<SystemPosition> = dept?.positions ?: TreeSet<SystemPosition>()
        return positions.map { position -> modelMapper.map(position, PositionDto::class.java) }.toList()
    }

    @Transactional
    fun save(positionDto: PositionDto): PositionDto {
        var systemPosition: SystemPosition = modelMapper.map(positionDto, SystemPosition::class.java)
        if (systemPosition.id == null) {
            systemPosition = this.systemPositionRepository.save(systemPosition)
        } else {
            this.systemPositionRepository.updateBasicProperties(systemPosition)
        }
        return modelMapper.map(systemPosition, PositionDto::class.java)
    }

    @Transactional
    fun deleteById(id: Short) {
        this.systemPositionRepository.deleteById(id)
    }

    @Transactional
    fun toggleStatus(id: Short) {
        this.systemPositionRepository.toggleStatus(id)
    }

}
