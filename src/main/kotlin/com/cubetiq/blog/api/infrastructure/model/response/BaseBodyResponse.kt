package com.cubetiq.blog.api.infrastructure.model.response

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

data class BaseBodyResponse(
    var status: Int? = 200,
    var message: String? = null,
)

data class ResponsePageObject(
    var totalPage: Int,
    var page: Int,
    var totalCount: Long,
    var pageSize: Int
)

data class ResponseStatusObject(
    var message: Any? = null,
    var code: Int? = 200,
)

data class BodyResponse(
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var data: Any? = null,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var pages: ResponsePageObject? = null,
    var status: ResponseStatusObject? = ResponseStatusObject(),
) {
    companion object {
        fun success(data: List<*>, message: String? = null): ResponseEntity<Any> {
            val status = ResponseStatusObject(
                message, 200
            )

            val responseObject = BodyResponse(
                data = data,
                status = status
            )

            return ResponseEntity.status(HttpStatus.OK).body(responseObject)
        }

        fun success(page: Page<*>, message: String? = null): ResponseEntity<Any> {
            val pageObject = if (page.pageable.isUnpaged) null else ResponsePageObject(
                page = page.number,
                totalPage = page.totalPages,
                totalCount = page.totalElements,
                pageSize = page.size
            )
            val status = ResponseStatusObject(
                message, 200
            )

            val responseObject = BodyResponse(
                data = page.content,
                pages = pageObject,
                status = status,
            )

            return ResponseEntity.status(HttpStatus.OK).body(responseObject)
        }

        fun success(obj: Any?, message: String? = null): ResponseEntity<Any> {
            val status = ResponseStatusObject(
                message, 200
            )
            val responseObject = BodyResponse(data = obj, status = status)
            return ResponseEntity.status(HttpStatus.OK).body(responseObject)
        }
    }
}