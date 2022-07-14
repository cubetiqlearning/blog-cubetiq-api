package com.cubetiq.blog.api.util

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

object JsonUtils {
    private var objectMapper: ObjectMapper? = null

    private fun getObjectMapper(renew: Boolean = false): ObjectMapper {
        if (this.objectMapper == null || renew) {
            objectMapper = jacksonObjectMapper()
        }
        return objectMapper!!
    }

    fun parseObjectToString(obj: Any?): String? {
        if (obj == null) return null
        return when (obj) {
            is String -> obj
            else -> try {
                this.getObjectMapper()
                    .writeValueAsString(obj)
            } catch (ex: Exception) {
                null
            }
        }
    }

    fun toJsonNode(any: Any?): JsonNode? {
        if (any == null) return null
        return when (any) {
            is JsonNode -> any
            else -> this.getObjectMapper().readTree(this.parseObjectToString(any))
        }
    }

    fun <T> fromJson(json: String?, typeRef: TypeReference<T>): T? {
        if (json == null) return null
        return jacksonObjectMapper().readValue(json, typeRef)
    }
}