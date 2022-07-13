package com.cubetiq.blog.api.service

import com.cubetiq.blog.api.model.entity.UserEntity
import com.cubetiq.blog.api.model.request.user.UserAuthRequest
import com.cubetiq.blog.api.model.response.user.UserLoginResponse
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
interface UserAuthService : UserDetailsService {
    fun getCurrentUser(): UserEntity

    fun getCurrentUserOrNull(): UserEntity?

    fun register(request: UserAuthRequest): UserEntity?

    fun login(request: UserAuthRequest): UserLoginResponse?
}