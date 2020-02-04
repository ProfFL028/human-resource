package com.smnsyh.hr.auth

import com.fasterxml.jackson.annotation.JsonProperty

class TokenResponse(
        @JsonProperty
        val token: String
)
