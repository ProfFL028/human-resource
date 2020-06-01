package com.smnsyh.hr.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.PrimaryKeyJoinColumn

@Entity
@PrimaryKeyJoinColumn(name = "data_permission_id")
data class SystemDataPermission(
        @Column(name = "AVAILABLE_DEPT")
        var availableDept: String? = null
) : SystemPermission() {

}
