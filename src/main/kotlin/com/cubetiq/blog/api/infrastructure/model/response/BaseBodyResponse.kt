package com.cubetiq.blog.api.infrastructure.model.response

data class BaseBodyResponse(
    var status: Int? = 200,
    var message: String? = null,
)