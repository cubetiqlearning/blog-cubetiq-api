package com.cubetiq.blog.api.model.response.category

import com.cubetiq.blog.api.model.entity.CategoryEntity
import com.cubetiq.blog.api.model.response.PostResponse

data class CategoryResponse(
    var id: Long? = null,
    var name: String? = null,
    var notes: String? = null,
    var posts: Collection<PostResponse>? = listOf(),
) {
    companion object {
        fun toEntity(entity: CategoryEntity?): CategoryResponse? {
            entity ?: return null

            return CategoryResponse(
                id = entity.id,
                name = entity.name,
                notes = entity.notes,
                posts = entity.post?.filter { it.deletedAt == null }?.mapNotNull {
                    PostResponse.toEntity(it)
                }
            )
        }
    }
}