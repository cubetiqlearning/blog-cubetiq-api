package com.cubetiq.blog.api.service

import com.cubetiq.blog.api.model.entity.CategoryEntity
import com.cubetiq.blog.api.model.request.category.CategoryRequest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
interface CategoryService {
    fun create(request: CategoryRequest): CategoryEntity?

    fun update(id: Long, request: CategoryRequest): CategoryEntity?

    fun findById(id: Long): CategoryEntity?

    fun delete(id: Long): CategoryEntity?

    fun softDelete(id: Long): CategoryEntity?

    fun findAllAvailable(): MutableList<CategoryEntity>

    fun findAllAvailable(pageable: Pageable): Page<CategoryEntity>
}