package com.cubetiq.blog.api.web.controller.frontend

import com.cubetiq.blog.api.constant.RestUriConstant
import com.cubetiq.blog.api.infrastructure.model.response.BodyResponse
import com.cubetiq.blog.api.model.response.category.AdminCategoryResponse
import com.cubetiq.blog.api.model.response.category.CategoryResponse
import com.cubetiq.blog.api.service.CategoryService
import io.swagger.annotations.Api
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Api(
    tags = ["Frontend Category"],
    description = "Welcome to frontend category api"
)
@RestController
@RequestMapping(RestUriConstant.Frontend.CATEGORY)
class FrontendCategoryController @Autowired constructor(
    private val categoryService: CategoryService,
) {
    @GetMapping("/{id}")
    fun findOne(
        @PathVariable id: Long,
    ): ResponseEntity<Any> {
        val data = categoryService.findById(id)
            ?: throw Exception("Category not found!")

        return BodyResponse.success(CategoryResponse.toEntity(data), message = "Find One Successful")
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
                AdminCategoryResponse.toEntity(it)
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

        return BodyResponse.success(data, message = "Find all Successful")
    }
}