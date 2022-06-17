package com.cubetiq.blog.api.service.implemetation

import com.cubetiq.blog.api.model.entity.CategoryEntity
import com.cubetiq.blog.api.repository.CategoryRepository
import com.cubetiq.blog.api.service.CategoryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CategoryServiceImpl @Autowired constructor(
    private val categoryRepository: CategoryRepository,
) : CategoryService {
    override fun create(request: CategoryEntity): CategoryEntity? {
        return categoryRepository.save(request)
    }
}