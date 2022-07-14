package com.cubetiq.blog.api.model.request.configuration

import com.cubetiq.blog.api.model.entity.ConfigurationEntity

data class ConfigurationRequest(
    var key: String? = null,
    var value: Map<String, Any?>? = mapOf(),
) {
    fun toEntity(): ConfigurationEntity {
        return ConfigurationEntity(
            key = this.key,
            value = this.value
        )
    }
}
