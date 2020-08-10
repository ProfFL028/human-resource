package com.smnsyh.hr.service

import com.smnsyh.hr.vo.PositionVO
import com.smnsyh.hr.entity.SystemDept
import com.smnsyh.hr.entity.SystemPosition
import com.smnsyh.hr.repository.SystemDeptRepository
import com.smnsyh.hr.repository.SystemPositionRepository
import org.modelmapper.ModelMapper
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
    fun findAllByOrderBySortNumber(): List<PositionVO> {
        var positions = this.systemPositionRepository.findAllByOrderBySortNumber()
        return positions.map { position -> modelMapper.map(position, PositionVO::class.java) }
    }

    @Transactional(readOnly = true)
    fun findAvailablePositions(deptId: Short): List<PositionVO> {
        var positions = this.systemPositionRepository.findAvailablePositions(deptId)
        return positions.map { position -> modelMapper.map(position, PositionVO::class.java) }
    }

    @Transactional(readOnly = true)
    fun findOwnedPositions(deptId: Short): List<PositionVO> {
        var dept: SystemDept? = this.systemDeptRepository.findById(deptId).orElse(null)
        var positions: MutableSet<SystemPosition> = dept?.positions ?: TreeSet<SystemPosition>()
        return positions.map { position -> modelMapper.map(position, PositionVO::class.java) }.toList()
    }

    @Transactional
    fun save(positionDto: PositionVO): PositionVO {
        var systemPosition: SystemPosition = modelMapper.map(positionDto, SystemPosition::class.java)
        if (systemPosition.id == null) {
            systemPosition = this.systemPositionRepository.save(systemPosition)
        } else {
            this.systemPositionRepository.updateBasicProperties(systemPosition)
        }
        return modelMapper.map(systemPosition, PositionVO::class.java)
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
