package com.cubetiq.blog.api.model.request.post

import com.cubetiq.blog.api.model.entity.PostEntity

data class PostRequest(
    var title: String? = null,
    var description: String? = null,
    var attachment: String? = null,
    var categoryId: String? = null
) {
    fun toEntity(): PostEntity {
        return PostEntity(
            title = this.title,
            description = this.description,
            attachment = this.attachment,
        )
    }
}