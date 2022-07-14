package com.cubetiq.blog.api.service

import com.cubetiq.blog.api.model.entity.ConfigurationEntity
import com.cubetiq.blog.api.model.request.configuration.ConfigurationRequest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
interface ConfigurationService {
    fun create(request: ConfigurationRequest): ConfigurationEntity?

    fun update(id: Long, request: ConfigurationRequest): ConfigurationEntity?

    fun findById(id: Long): ConfigurationEntity?

    fun findByKey(key: String): ConfigurationEntity?

    fun delete(id: Long): ConfigurationEntity?

    fun softDelete(id: Long): ConfigurationEntity?

    fun findAllAvailable(): MutableList<ConfigurationEntity>

    fun findAllAvailable(pageable: Pageable): Page<ConfigurationEntity>
}