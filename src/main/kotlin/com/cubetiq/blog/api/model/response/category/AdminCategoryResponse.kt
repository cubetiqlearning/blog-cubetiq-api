package com.cubetiq.blog.api.model.response.category

import com.cubetiq.blog.api.model.entity.CategoryEntity

data class AdminCategoryResponse(
    var id: Long? = null,
    var name: String? = null,
    var notes: String? = null,
) {
    companion object {
        fun toEntity(entity: CategoryEntity?): AdminCategoryResponse? {
            entity ?: return null

            return AdminCategoryResponse(
                id = entity.id,
                name = entity.name,
                notes = entity.notes,
            )
        }
    }
}