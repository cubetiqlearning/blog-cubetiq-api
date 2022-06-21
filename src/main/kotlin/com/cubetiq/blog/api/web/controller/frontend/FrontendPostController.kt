package com.cubetiq.blog.api.web.controller.frontend

import com.cubetiq.blog.api.constant.RestUriConstant
import com.cubetiq.blog.api.model.request.PostRequest
import com.cubetiq.blog.api.model.response.PostResponse
import com.cubetiq.blog.api.service.PostService
import io.swagger.annotations.Api
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
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

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(PostResponse.toEntity(data))
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody request: PostRequest,
    ): ResponseEntity<Any> {
        val data = postService.update(id, request)

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(PostResponse.toEntity(data))
    }

    @GetMapping("/{id}")
    fun findOne(
        @PathVariable id: Long,
    ): ResponseEntity<Any> {
        val data = postService.findById(id)
            ?: throw Exception("Post not found!")

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(PostResponse.toEntity(data))
    }

    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable id: Long,
    ): ResponseEntity<Any> {
        val data = postService.softDelete(id)

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(PostResponse.toEntity(data))
    }

    @GetMapping
    fun findAllAvailable(): ResponseEntity<Any> {
        val data = postService.findAllAvailable(Pageable.ofSize(20)).map {
            PostResponse.toEntity(it)
        }

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(data.content)
    }
}