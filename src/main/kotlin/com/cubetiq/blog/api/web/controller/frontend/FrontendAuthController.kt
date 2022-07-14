package com.cubetiq.blog.api.web.controller.frontend

import com.cubetiq.blog.api.constant.RestUriConstant
import com.cubetiq.blog.api.infrastructure.model.response.BodyResponse
import com.cubetiq.blog.api.model.request.user.UserAuthRequest
import com.cubetiq.blog.api.model.response.user.UserResponse
import com.cubetiq.blog.api.service.UserAuthService
import io.swagger.annotations.Api
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@Api(
    tags = ["Frontend Authentication"],
    description = "Welcome to frontend authentication api"
)
@RestController
@RequestMapping(RestUriConstant.Frontend.AUTH)
class FrontendAuthController @Autowired constructor(
    private val userAuthService: UserAuthService,
) {
    @PostMapping("/register")
    fun register(
        @RequestBody request: UserAuthRequest,
    ): ResponseEntity<Any> {
        val data = userAuthService.register(request)

        return BodyResponse.success(
            UserResponse.toEntity(data),
            message = "Register Successful",
        )
    }

    @GetMapping("/me")
    fun fetchMe(): ResponseEntity<Any> {
        val data = userAuthService.getCurrentUser()

        return BodyResponse.success(UserResponse.toEntity(data), message = "Fetch me Successful")
    }

    @PostMapping("/login")
    fun login(
        @RequestBody request: UserAuthRequest,
    ): ResponseEntity<Any> {
        val data = userAuthService.login(request)

        return BodyResponse.success(
            data,
            message = "Login Successful",
        )
    }

    @PutMapping("/logout/{userId}")
    @PreAuthorize("""hasRole("USER")""")
    fun login(
        @PathVariable userId: Long,
    ): ResponseEntity<Any> {
        return BodyResponse.success(
            mapOf(
                "id" to userId
            ),
            message = "Login Successful",
        )
    }
}