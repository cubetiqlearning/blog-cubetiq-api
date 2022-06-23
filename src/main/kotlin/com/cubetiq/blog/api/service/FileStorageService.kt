package com.cubetiq.blog.api.service

import com.cubetiq.blog.api.model.response.UploadResponse
import com.cubetiq.blog.api.property.AppProperties
import com.cubetiq.blog.api.property.StorageProperties
import com.cubetiq.blog.api.util.FileResourceUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*

@Service
class FileStorageService @Autowired constructor(
    private val storageProperties: StorageProperties,
    private val appProperties: AppProperties,
) {
    private val getFilePath get() = appProperties.getFilePath()

    fun uploadFile(multipartFile: MultipartFile): UploadResponse {
        val uuid = UUID.randomUUID().toString()
        val dirString = storageProperties.getFileStorageDirectory()

        val saveFile = FileResourceUtil.saveMultipartToFile(multipartFile, dirString, uuid)
        val filename = saveFile.name
        val fullPath = "${storageProperties.getFullStoragePath()}/$filename"
        val uriPath = "${storageProperties.getStorageUriPath()}/$filename"

        return UploadResponse(
            shortLink = uriPath,
            uid = uuid,
            name = multipartFile.originalFilename,
            url = fullPath,
            type = multipartFile.contentType,
            size = saveFile.length(),
        )
    }

    fun loadFile(filename: String): Resource {
        val file: Path = Paths.get(getFilePath).resolve(filename)
        val resource: Resource = UrlResource(file.toUri())
        if (resource.exists() || resource.isReadable)
            return resource
        else
            throw Exception("Could not read the file!")
    }
}