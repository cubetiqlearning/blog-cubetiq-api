package com.cubetiq.blog.api.model.request

import com.cubetiq.blog.api.model.entity.PostEntity

data class PostRequest(
    var title: String? = null,
    var description: String? = null,
    var attachment: String? = null,
    var categoryId: String? = null,
    var userId: String? = null,
) {
    fun toEntity(): PostEntity {
        return PostEntity(
            title = this.title,
            description = this.description,
            attachment = this.attachment,
        )
    }
}