package com.cubetiq.blog.api.model.response.user

import com.cubetiq.blog.api.model.entity.UserEntity
import com.cubetiq.blog.api.module.security.util.JwtUtils

data class UserLoginResponse(
    var token: String? = null,
) {
    companion object {
        fun toEntity(entity: UserEntity?): UserLoginResponse? {
            entity ?: return null
            val _token = JwtUtils.encryptToken(entity.getUserAuthDetails())

            return UserLoginResponse(
                token = _token
            )
        }
    }
}