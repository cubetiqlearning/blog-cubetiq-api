package com.cubetiq.blog.api.service

import com.cubetiq.blog.api.model.entity.UserEntity
import org.springframework.stereotype.Service

@Service
interface UserService {
    fun findById(id: Long): UserEntity?

    fun findByUsername(username: String): UserEntity?
}