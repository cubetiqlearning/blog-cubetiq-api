package com.cubetiq.blog.api.repository

import com.cubetiq.blog.api.model.entity.ConfigurationEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ConfigurationRepository : JpaRepository<ConfigurationEntity, Long> {
    fun existsByKeyAndDeletedAtIsNull(key: String): Boolean

    fun queryAllByDeletedAtIsNull(): MutableList<ConfigurationEntity>

    fun queryAllByDeletedAtIsNull(pageable: Pageable): Page<ConfigurationEntity>

    fun queryByIdAndDeletedAtIsNull(id: Long): ConfigurationEntity?

    fun queryByKeyAndDeletedAtIsNull(key: String): ConfigurationEntity?
}