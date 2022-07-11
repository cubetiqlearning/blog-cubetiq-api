package com.cubetiq.blog.api.service.implemetation

import com.cubetiq.blog.api.model.entity.UserEntity
import com.cubetiq.blog.api.repository.UserRepository
import com.cubetiq.blog.api.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserServiceImpl @Autowired constructor(
    private val userRepository: UserRepository,
) : UserService {
    override fun findById(id: Long): UserEntity? {
        return userRepository.queryByIdAndDeletedAtIsNull(id)
    }

    override fun findByUsername(username: String): UserEntity? {
        return userRepository.queryByUsernameAndDeletedAtIsNull(username)
    }
}