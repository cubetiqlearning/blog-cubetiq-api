package com.cubetiq.blog.api.util

import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.util.*

object FileResourceUtil {
    fun saveMultipartToFile(
        resource: MultipartFile,
        path: String,
        // null or empty use UUID
        filename: String? = null,
    ): File {
        val name = if (filename.isNullOrEmpty()) {
            UUID.randomUUID().toString()
        } else {
            filename
        }

        val destPath = if (path.isEmpty() || path.isBlank()) {
            "./"
        } else {
            path
        }

        val sourceFilename = resource.originalFilename ?: resource.name
        val extension = sourceFilename.split(".").last()

        val destFile = File("$destPath/$name.$extension")

        if (!destFile.exists()) {
            destFile.mkdirs()
        } else {
            println("Destination File existed!")
        }

        resource.transferTo(destFile)

        return destFile
    }
}