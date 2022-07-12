package com.cubetiq.blog.api.model.response.file

data class UploadResponse(
    var uid: String? = null,
    var name: String? = null,
    var url: String? = null,
    var size: Long? = null,
    var type: String? = null,
    var shortLink: String? = null
)