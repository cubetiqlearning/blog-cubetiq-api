package com.cubetiq.blog.api.web.controller.backend

import com.cubetiq.blog.api.constant.RestUriConstant
import com.cubetiq.blog.api.model.request.CategoryRequest
import com.cubetiq.blog.api.model.response.CategoryResponse
import com.cubetiq.blog.api.service.CategoryService
import io.swagger.annotations.Api
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Api(
    tags = ["Backend Category"],
    description = "Welcome to backend category api"
)
@RestController
@RequestMapping(value = [RestUriConstant.Backend.CATEGORY])
class BackendCategoryController @Autowired constructor(
    private val categoryService: CategoryService,
) {
    @PostMapping
    fun create(
        @RequestBody request: CategoryRequest
    ): ResponseEntity<Any> {
        val data = categoryService.create(request)

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(CategoryResponse.toEntity(data))
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody request: CategoryRequest,
    ): ResponseEntity<Any> {
        val data = categoryService.update(id, request)

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(CategoryResponse.toEntity(data))
    }

    @GetMapping("/{id}")
    fun findOne(
        @PathVariable id: Long,
    ): ResponseEntity<Any> {
        val data = categoryService.findById(id)
            ?: throw Exception("Category not found!")

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(CategoryResponse.toEntity(data))
    }

    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable id: Long,
    ): ResponseEntity<Any> {
        val data = categoryService.softDelete(id)

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(CategoryResponse.toEntity(data))
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
            categoryService.findAllAvailable(Pageable.unpaged()).map {
                CategoryResponse.toEntity(it)
            }
        } else {
            val sb = if (sortBy == "desc") {
                Sort.by(sort).descending()
            } else {
                Sort.by(sort).ascending()
            }
            categoryService.findAllAvailable(PageRequest.of(page, size, sb)).map {
                CategoryResponse.toEntity(it)
            }
        }

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(data.content)
    }
}