package com.smnsyh.hr.auth

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken

class AuthenticationWithToken : PreAuthenticatedAuthenticationToken {
    constructor(principal: Any?, credentials: Any?) : super(principal, credentials)

    constructor(principal: Any?, credentials: Any?, authorities: Collection<GrantedAuthority?>) : super(principal, credentials, authorities)

    var token: String? = null
}
