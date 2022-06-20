package com.cubetiq.blog.api.service.implemetation

import com.cubetiq.blog.api.model.entity.CategoryEntity
import com.cubetiq.blog.api.model.request.CategoryRequest
import com.cubetiq.blog.api.repository.CategoryRepository
import com.cubetiq.blog.api.service.CategoryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class CategoryServiceImpl @Autowired constructor(
    private val categoryRepository: CategoryRepository,
) : CategoryService {
    override fun create(request: CategoryRequest): CategoryEntity? {
        if (existsByName(request.name!!))
            throw Exception("Category name already exists!")

        return categoryRepository.save(request.toEntity())
    }

    override fun update(id: Long, request: CategoryRequest): CategoryEntity? {
        val data = findById(id)
            ?: throw Exception("Category not found!")

        if (data.name != request.name)
            if (existsByName(request.name!!))
                throw Exception("Category name already exists!")

        data.name = request.name ?: data.name
        data.notes = request.notes ?: data.notes

        return categoryRepository.save(data)
    }

    override fun findById(id: Long): CategoryEntity? {
        return categoryRepository.queryByIdAndDeletedAtIsNull(id)
    }

    @Transactional
    override fun delete(id: Long): CategoryEntity? {
        val data = findById(id)
            ?: throw Exception("Category not found!")

        categoryRepository.deleteById(data.id!!)

        return data
    }

    override fun softDelete(id: Long): CategoryEntity? {
        val data = findById(id)
            ?: throw Exception("Category not found!")

        data.deletedAt = Date()

        return categoryRepository.save(data)
    }

    override fun findAllAvailable(): MutableList<CategoryEntity> {
        return categoryRepository.queryAllByDeletedAtIsNull()
    }

    override fun findAllAvailable(pageable: Pageable): Page<CategoryEntity> {
        return categoryRepository.queryAllByDeletedAtIsNull(pageable)
    }

    private fun existsByName(name: String): Boolean {
        return categoryRepository.existsByNameAndDeletedAtIsNull(name)
    }
}