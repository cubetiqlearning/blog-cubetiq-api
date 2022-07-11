package com.cubetiq.blog.api.service.implemetation

import com.cubetiq.blog.api.module.security.UserAuthDetails
import com.cubetiq.blog.api.service.UserAuthService
import com.cubetiq.blog.api.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class UserAuthServiceImpl @Autowired constructor(
    private val userService: UserService,
) : UserAuthService {
    override fun loadUserByUsername(username: String?): UserDetails? {
        val user = userService.findByUsername(username!!) ?: return null
        return UserAuthDetails(user)
    }
}