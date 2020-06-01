package com.smnsyh.hr.entity

import javax.persistence.*

@Entity
@PrimaryKeyJoinColumn(name = "operation_id")
data class SystemOperationPermission(
        @MapsId
        @OneToOne(fetch = FetchType.LAZY)
        @JoinColumn(name="menu_id")
        var menu: SystemMenuPermission? = null
) : SystemPermission() {
}
