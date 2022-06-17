package com.cubetiq.blog.api.service

import com.cubetiq.blog.api.model.entity.CategoryEntity
import org.springframework.stereotype.Service

@Service
interface CategoryService {
    fun create(request: CategoryEntity): CategoryEntity?
}