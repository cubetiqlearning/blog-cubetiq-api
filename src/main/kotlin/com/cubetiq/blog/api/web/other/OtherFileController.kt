package com.cubetiq.blog.api.web.other

import com.cubetiq.blog.api.service.FileStorageService
import io.swagger.annotations.Api
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.util.FileCopyUtils
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import javax.servlet.http.HttpServletResponse

@Api(
    tags = ["Other File"],
    description = " "
)
@RestController
@RequestMapping("/files")
class OtherFileController @Autowired constructor(
    private val fileStorageService: FileStorageService,
) {
    @PostMapping
    @PreAuthorize("""hasRole("USER")""")
    fun uploadFile(
        @RequestPart("file") file: MultipartFile
    ): ResponseEntity<Any> {
        val fileResponse = fileStorageService.uploadFile(file)

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(fileResponse)
    }

    @GetMapping("/{fileName:.+}")
    fun getImage(
        @PathVariable fileName: String,
        response: HttpServletResponse,
    ) {
        val resource: Resource = fileStorageService.loadFile(fileName)

        response.setContentLengthLong(resource.file.length())
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.filename + "\"")

        FileCopyUtils.copy(resource.inputStream, response.outputStream)
    }
}