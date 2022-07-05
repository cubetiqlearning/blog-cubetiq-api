package com.cubetiq.blog.api.web.controller.frontend

import com.cubetiq.blog.api.constant.RestUriConstant
import com.cubetiq.blog.api.infrastructure.model.response.BodyResponse
import com.cubetiq.blog.api.model.request.PostRequest
import com.cubetiq.blog.api.model.response.PostResponse
import com.cubetiq.blog.api.service.PostService
import io.swagger.annotations.Api
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Api(
    tags = ["Frontend Post"],
    description = "Welcome to frontend post api"
)
@RestController
@RequestMapping(RestUriConstant.Frontend.POST)
class FrontendPostController @Autowired constructor(
    private val postService: PostService,
) {
    @PostMapping
    fun create(
        @RequestBody request: PostRequest
    ): ResponseEntity<Any> {
        val data = postService.create(request)

        return BodyResponse.success(PostResponse.toEntity(data), message = "Create Successful")
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody request: PostRequest,
    ): ResponseEntity<Any> {
        val data = postService.update(id, request)

        return BodyResponse.success(PostResponse.toEntity(data), message = "Update Successful")
    }

    @GetMapping("/{id}")
    fun findOne(
        @PathVariable id: Long,
    ): ResponseEntity<Any> {
        val data = postService.findById(id)
            ?: throw Exception("Post not found!")

        return BodyResponse.success(PostResponse.toEntity(data), message = "Find One Successful")
    }

    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable id: Long,
    ): ResponseEntity<Any> {
        val data = postService.softDelete(id)

       return BodyResponse.success(PostResponse.toEntity(data), message = "Delete Successful")
    }

    @GetMapping("/all/{categoryId}")
    fun findAllAvailable(
        @PathVariable categoryId: Long,
        @RequestParam(required = false, defaultValue = "0") page: Int,
        @RequestParam(required = false, defaultValue = "20") size: Int,
        @RequestParam(required = false, defaultValue = "id") sort: String,
        @RequestParam(required = false, defaultValue = "desc") sortBy: String,
        @RequestParam(required = false, defaultValue = "false") paged: Boolean,
    ): ResponseEntity<Any> {
        val data = if (paged) {
            postService.findAllAvailable(categoryId, Pageable.unpaged()).map {
                PostResponse.toEntity(it)
            }
        } else {
            val sb = if (sortBy == "desc") {
                Sort.by(sort).descending()
            } else {
                Sort.by(sort).ascending()
            }

            if (categoryId == 0L) {
                postService.findAllAvailable(PageRequest.of(page, size, sb)).map {
                    PostResponse.toEntity(it)
                }
            } else {
                postService.findAllAvailable(categoryId, PageRequest.of(page, size, sb)).map {
                    PostResponse.toEntity(it)
                }
            }
        }

        return BodyResponse.success(data, message = "Find all Successful")
    }
}