package com.cubetiq.blog.api.module.security

import com.cubetiq.blog.api.infrastructure.extension.toJsonNode
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthenticationExceptionEntryPoint: AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?
    ) {
        authException?.printStackTrace()

        val responseBody = ResponseEntity.status(HttpStatus.FORBIDDEN).body(
            mapOf(
                "message" to authException?.message
            )
        )

        response?.status = HttpStatus.FORBIDDEN.value()
        response?.contentType = MediaType.APPLICATION_JSON_VALUE
        val writer = response?.writer
        writer?.print(responseBody.toJsonNode())
        writer?.flush()
        writer?.close()
    }
}