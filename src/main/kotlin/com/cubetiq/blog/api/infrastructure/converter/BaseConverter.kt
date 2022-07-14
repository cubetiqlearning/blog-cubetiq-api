package com.cubetiq.blog.api.infrastructure.converter

import com.cubetiq.blog.api.infrastructure.extension.fromJson
import com.cubetiq.blog.api.infrastructure.extension.toJson
import com.fasterxml.jackson.core.type.TypeReference
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter
abstract class BaseConverter<T : Any?> : AttributeConverter<T, String?> {
    private val log: Logger = LoggerFactory.getLogger(BaseConverter::class.java)
    override fun convertToDatabaseColumn(attribute: T): String? {
        return try {
            attribute.toJson()
        } catch (ex: Exception) {
            log.warn("Generic Object Converter: JSON writing error: $ex", javaClass)
            null
        }
    }

    override fun convertToEntityAttribute(dbData: String?): T? {
        return try {
            dbData.fromJson(toObject())
        } catch (ex: Exception) {
            log.warn("Generic Object Converter: JSON reading error: $ex", javaClass)
            null
        }
    }

    abstract fun toObject(): TypeReference<T>
}