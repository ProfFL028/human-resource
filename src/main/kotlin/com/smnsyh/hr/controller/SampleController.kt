package com.smnsyh.hr.controller

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.RestController

@RestController
@PreAuthorize("hasAuthority('ROLE_USER')")
class SampleController(
) {

}
