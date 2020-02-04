package com.smnsyh.hr.auth

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken

class BackendAdminUsernamePasswordAuthenticationToken : UsernamePasswordAuthenticationToken {
    constructor(principal: Any?, credentials: Any?) : super(principal, credentials)
}
