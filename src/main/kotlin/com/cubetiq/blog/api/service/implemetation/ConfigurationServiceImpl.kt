package com.cubetiq.blog.api.service.implemetation

import com.cubetiq.blog.api.exception.AlreadyExistsException
import com.cubetiq.blog.api.model.entity.ConfigurationEntity
import com.cubetiq.blog.api.model.request.configuration.ConfigurationRequest
import com.cubetiq.blog.api.repository.ConfigurationRepository
import com.cubetiq.blog.api.service.ConfigurationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class ConfigurationServiceImpl @Autowired constructor(
    private val configurationRepository: ConfigurationRepository,
) : ConfigurationService {
    override fun create(request: ConfigurationRequest): ConfigurationEntity? {
        if (existsByKey(request.key!!))
            throw AlreadyExistsException("Configuration key already exists!")

        return configurationRepository.save(request.toEntity())
    }

    override fun update(id: Long, request: ConfigurationRequest): ConfigurationEntity? {
        val data = findById(id)
            ?: throw Exception("Configuration not found!")

        if (data.key != request.key)
            if (existsByKey(request.key!!))
                throw Exception("Configuration key already exists!")

        data.key = request.key ?: data.key
        data.value = request.value ?: data.value

        return configurationRepository.save(data)
    }

    override fun findById(id: Long): ConfigurationEntity? {
        return configurationRepository.queryByIdAndDeletedAtIsNull(id)
    }

    override fun findByKey(key: String): ConfigurationEntity? {
        return configurationRepository.queryByKeyAndDeletedAtIsNull(key)
    }

    @Transactional
    override fun delete(id: Long): ConfigurationEntity? {
        val data = findById(id)
            ?: throw Exception("Configuration not found!")

        configurationRepository.deleteById(data.id!!)

        return data
    }

    override fun softDelete(id: Long): ConfigurationEntity? {
        val data = findById(id)
            ?: throw Exception("Configuration not found!")

        data.deletedAt = Date()

        return configurationRepository.save(data)
    }

    override fun findAllAvailable(): MutableList<ConfigurationEntity> {
        return configurationRepository.queryAllByDeletedAtIsNull()
    }

    override fun findAllAvailable(pageable: Pageable): Page<ConfigurationEntity> {
        return configurationRepository.queryAllByDeletedAtIsNull(pageable)
    }

    private fun existsByKey(key: String): Boolean {
        return configurationRepository.existsByKeyAndDeletedAtIsNull(key)
    }
}