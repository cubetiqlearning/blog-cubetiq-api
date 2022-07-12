package com.cubetiq.blog.api.util

import com.cubetiq.blog.api.module.security.util.JwtUtils

object TextUtils {
    fun encryptedPassword(password: String): String {
        val passwordEncoder = JwtUtils.getPasswordEncoder()
        return passwordEncoder.encode(password)
    }

    fun validatePassword(password: String, encryptedPassword: String): Boolean {
        val passwordEncoder = JwtUtils.getPasswordEncoder()
        return passwordEncoder.matches(password, encryptedPassword)
    }
}