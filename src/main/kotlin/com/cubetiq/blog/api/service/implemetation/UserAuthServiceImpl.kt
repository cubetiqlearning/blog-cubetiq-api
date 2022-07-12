package com.cubetiq.blog.api.service.implemetation

import com.cubetiq.blog.api.exception.AlreadyExistsException
import com.cubetiq.blog.api.exception.UserNotFoundException
import com.cubetiq.blog.api.model.entity.UserEntity
import com.cubetiq.blog.api.model.request.user.UserAuthRequest
import com.cubetiq.blog.api.model.response.user.UserLoginResponse
import com.cubetiq.blog.api.module.security.UserAuthDetails
import com.cubetiq.blog.api.repository.RoleRepository
import com.cubetiq.blog.api.repository.UserRepository
import com.cubetiq.blog.api.service.UserAuthService
import com.cubetiq.blog.api.service.UserService
import com.cubetiq.blog.api.util.TextUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class UserAuthServiceImpl @Autowired constructor(
    private val userService: UserService,
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
) : UserAuthService {
    override fun register(request: UserAuthRequest): UserEntity? {
        if (userService.existsByUsername(request.username!!))
            throw AlreadyExistsException("Username already exists!")
        val role = roleRepository.queryByNameAndDeletedAtIsNull("USER") ?: null

        val data = request.toEntity()
        data.password = TextUtils.encryptedPassword(request.password!!)
        data.roles = mutableListOf(role!!)

        return try {
            userRepository.save(data)
        } catch (ex: Exception) {
            throw Exception(ex)
        }
    }

    override fun login(request: UserAuthRequest): UserLoginResponse? {
        val user = userService.findByUsername(request.username!!)

        if (user != null && TextUtils.validatePassword(request.password!!, user.password!!))
            return UserLoginResponse.toEntity(user)!!

        throw UserNotFoundException("Incorrect username or password!")
    }

    override fun loadUserByUsername(username: String?): UserDetails? {
        val user = userService.findByUsername(username!!) ?: return null
        return UserAuthDetails(user)
    }
}