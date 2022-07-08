package com.cubetiq.blog.api.service

import org.springframework.stereotype.Service

@Service("auth")
class AuthService {
    fun enable(): Boolean { return false }
}