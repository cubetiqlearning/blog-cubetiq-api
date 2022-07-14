package com.cubetiq.blog.api.infrastructure.converter

import com.fasterxml.jackson.core.type.TypeReference
import javax.persistence.Converter

@Converter
class MapConverter: BaseConverter<Map<String, Any?>>() {
    override fun toObject(): TypeReference<Map<String, Any?>> {
        return object : TypeReference<Map<String, Any?>>() {}
    }
}