package com.cubetiq.blog.api.web.controller.backend

import com.cubetiq.blog.api.constant.RestUriConstant
import com.cubetiq.blog.api.infrastructure.model.response.BodyResponse
import com.cubetiq.blog.api.model.request.category.CategoryRequest
import com.cubetiq.blog.api.model.request.configuration.ConfigurationRequest
import com.cubetiq.blog.api.model.response.category.AdminCategoryResponse
import com.cubetiq.blog.api.model.response.configuration.ConfigurationResponse
import com.cubetiq.blog.api.service.CategoryService
import com.cubetiq.blog.api.service.ConfigurationService
import io.swagger.annotations.Api
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@Api(
    tags = ["Backend Configuration"],
    description = "Welcome to backend configuration api"
)
@RestController
@RequestMapping(value = [RestUriConstant.Backend.CONFIGURATION])
@PreAuthorize("""hasAnyRole("ADMIN", "SUPER_ADMIN")""")
class BackendConfigurationController @Autowired constructor(
    private val configurationService: ConfigurationService,
) {
    @PostMapping
    fun create(
        @RequestBody request: ConfigurationRequest
    ): ResponseEntity<Any> {
        val data = configurationService.create(request)

        return BodyResponse.success(ConfigurationResponse.toEntity(data), message = "Create Successful")
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody request: ConfigurationRequest,
    ): ResponseEntity<Any> {
        val data = configurationService.update(id, request)

        return BodyResponse.success(ConfigurationResponse.toEntity(data), message = "Update Successful")
    }

    @GetMapping("/{id}")
    fun findOne(
        @PathVariable id: Long,
    ): ResponseEntity<Any> {
        val data = configurationService.findById(id)
            ?: throw Exception("Configuration not found!")

        return BodyResponse.success(ConfigurationResponse.toEntity(data), message = "Find one Successful")
    }

    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable id: Long,
    ): ResponseEntity<Any> {
        val data = configurationService.softDelete(id)

        return BodyResponse.success(ConfigurationResponse.toEntity(data), message = "Delete Successful")
    }

    @GetMapping
    fun findAllAvailable(
        @RequestParam(required = false, defaultValue = "0") page: Int,
        @RequestParam(required = false, defaultValue = "20") size: Int,
        @RequestParam(required = false, defaultValue = "id") sort: String,
        @RequestParam(required = false, defaultValue = "desc") sortBy: String,
        @RequestParam(required = false, defaultValue = "false") paged: Boolean,
    ): ResponseEntity<Any> {
        val data = if (paged) {
            configurationService.findAllAvailable(Pageable.unpaged()).map {
                ConfigurationResponse.toEntity(it)
            }
        } else {
            val sb = if (sortBy == "desc") {
                Sort.by(sort).descending()
            } else {
                Sort.by(sort).ascending()
            }
            configurationService.findAllAvailable(PageRequest.of(page, size, sb)).map {
                ConfigurationResponse.toEntity(it)
            }
        }

        return BodyResponse.success(data, message = "Find all Successful")
    }
}