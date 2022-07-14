package com.cubetiq.blog.api.model.response.configuration

import com.cubetiq.blog.api.model.entity.ConfigurationEntity

data class ConfigurationResponse(
    var id: Long? = null,
    var key: String? = null,
    var value: Map<String, Any?>? = mapOf(),
) {
    companion object {
        fun toEntity(entity: ConfigurationEntity?): ConfigurationResponse? {
            entity ?: return null

            return ConfigurationResponse(
                id = entity.id,
                key = entity.key,
                value = entity.value,
            )
        }
    }
}