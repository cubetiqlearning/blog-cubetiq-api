package com.cubetiq.blog.api.model.request

import com.cubetiq.blog.api.model.entity.CategoryEntity

data class CategoryRequest(
    var name: String? = null,
    var notes: String? = null,
) {
    fun toEntity(): CategoryEntity {
        return CategoryEntity(
            name = this.name,
            notes = this.notes
        )
    }
}
