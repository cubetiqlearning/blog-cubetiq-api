package com.cubetiq.blog.api.repository

import com.cubetiq.blog.api.model.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<UserEntity, Long> {
    fun queryByIdAndDeletedAtIsNull(id: Long): UserEntity?

    fun queryByUsernameAndDeletedAtIsNull(username: String): UserEntity?

    fun existsByUsernameAndDeletedAtIsNull(username: String): Boolean
}