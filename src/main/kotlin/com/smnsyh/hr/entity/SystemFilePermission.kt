package com.smnsyh.hr.entity

import javax.persistence.Entity
import javax.persistence.PrimaryKeyJoinColumn

@Entity
@PrimaryKeyJoinColumn(name = "file_id")
data class SystemFilePermission(
        var filePath: String? = null
) : SystemPermission() {

}
