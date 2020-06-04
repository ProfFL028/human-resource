package com.smnsyh.hr.entity

import javax.persistence.Entity
import javax.persistence.PrimaryKeyJoinColumn
import javax.persistence.Table

@Entity
@PrimaryKeyJoinColumn(name = "file_id")
data class SystemFilePermission(
        var filePath: String? = null
) : SystemPermission() {

}
