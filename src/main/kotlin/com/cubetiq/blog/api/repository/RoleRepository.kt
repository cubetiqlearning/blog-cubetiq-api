package com.cubetiq.blog.api.repository

import com.cubetiq.blog.api.model.entity.RoleEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RoleRepository : JpaRepository<RoleEntity, Long> {
    fun queryByNameAndDeletedAtIsNull(name: String): RoleEntity?
}