package com.cubetiq.blog.api.web.controller.backend

import com.cubetiq.blog.api.constant.RestUriConstant
import com.cubetiq.blog.api.model.entity.CategoryEntity
import com.cubetiq.blog.api.service.CategoryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = [RestUriConstant.Backend.CATEGORY])
class BackendCategoryController @Autowired constructor(
    private val categoryService: CategoryService,
) {
    @PostMapping
    fun create(
        @RequestBody request: CategoryEntity
    ): ResponseEntity<Any> {
        val data = categoryService.create(request)

        return ResponseEntity.ok(data)
    }
}