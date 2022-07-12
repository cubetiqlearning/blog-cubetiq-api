package com.cubetiq.blog.api.model.request.user

import com.cubetiq.blog.api.model.entity.UserEntity

data class UserAuthRequest(
    var username: String? = null,
    var password: String? = null,
) {
    fun toEntity(): UserEntity {
        return UserEntity(
            username = this.username,
            password = this.password
        )
    }
}