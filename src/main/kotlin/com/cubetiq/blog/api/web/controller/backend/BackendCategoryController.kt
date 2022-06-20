package com.cubetiq.blog.api.web.controller.backend

import com.cubetiq.blog.api.constant.RestUriConstant
import com.cubetiq.blog.api.model.entity.CategoryEntity
import com.cubetiq.blog.api.model.request.CategoryRequest
import com.cubetiq.blog.api.service.CategoryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

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

        return ResponseEntity.ok(data)
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody request: CategoryRequest,
    ): ResponseEntity<Any> {
        val data = categoryService.update(id, request)

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(data)
    }

    @GetMapping("/{id}")
    fun findOne(
        @PathVariable id: Long,
    ): ResponseEntity<Any> {
        val data = categoryService.findById(id)
            ?: throw Exception("Category not found!")

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(data)
    }

    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable id: Long,
    ): ResponseEntity<Any> {
        val data = categoryService.delete(id)

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(data)
    }

    @DeleteMapping("/soft/{id}")
    fun softDelete(
        @PathVariable id: Long,
    ): ResponseEntity<Any> {
        val data = categoryService.softDelete(id)

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(data)
    }

    @GetMapping
    fun findAllAvailable(): ResponseEntity<Any> {
        val data = categoryService.findAllAvailable()

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(data)
    }

    @GetMapping("/page")
    fun findAllAvailableByPage(): ResponseEntity<Any> {
        val data = categoryService.findAllAvailable(Pageable.ofSize(2))

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(data.content)
    }
}