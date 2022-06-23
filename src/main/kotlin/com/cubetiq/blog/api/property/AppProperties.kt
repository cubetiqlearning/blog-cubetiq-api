package com.cubetiq.blog.api.property

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class AppProperties(
    @Value("\${cubetiq.file.path:}")
    private val filePath: String,

    @Value("\${cubetiq.app.api-origin:}")
    private val apiOrigin: String? = null,
) {
    fun getFilePath(): String = this.filePath

    fun getApiOrigin(): String? = apiOrigin
}