package com.cubetiq.blog.api.web.controller.frontend

import com.cubetiq.blog.api.constant.RestUriConstant
import com.cubetiq.blog.api.infrastructure.model.response.BodyResponse
import com.cubetiq.blog.api.model.response.configuration.ConfigurationResponse
import com.cubetiq.blog.api.service.ConfigurationService
import io.swagger.annotations.Api
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Api(
    tags = ["Frontend Configuration"],
    description = "Welcome to frontend configuration api"
)
@RestController
@RequestMapping(RestUriConstant.Frontend.CONFIGURATION)
class FrontendConfigurationController @Autowired constructor(
    private val configurationService: ConfigurationService,
) {
    @GetMapping("all-keys")
    fun getAllKeys(): ResponseEntity<Any> {
        val data = configurationService.findAllAvailable().map {
            mapOf("key" to it.key)
        }

        return BodyResponse.success(
            data,
            message = "Find all Successful"
        )
    }

    @GetMapping("/{key}")
    fun getByKey(
        @PathVariable key: String,
    ): ResponseEntity<Any> {
        val data = configurationService.findByKey(key)

        return BodyResponse.success(
            ConfigurationResponse.toEntity(data),
            message = "Find one Successful"
        )
    }
}