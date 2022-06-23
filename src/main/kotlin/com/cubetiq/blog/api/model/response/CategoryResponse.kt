package com.cubetiq.blog.api.model.response

import com.cubetiq.blog.api.model.entity.CategoryEntity

data class CategoryResponse(
    var id: Long? = null,
    var name: String? = null,
    var notes: String? = null,
) {
    companion object {
        fun toEntity(entity: CategoryEntity?): CategoryResponse? {
            entity ?: return null

            return CategoryResponse(
                id = entity.id,
                name = entity.name,
                notes = entity.notes,
            )
        }
    }
}