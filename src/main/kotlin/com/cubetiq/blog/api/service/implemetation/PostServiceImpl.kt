package com.cubetiq.blog.api.service.implemetation

import com.cubetiq.blog.api.model.entity.PostEntity
import com.cubetiq.blog.api.model.request.post.PostRequest
import com.cubetiq.blog.api.repository.CategoryRepository
import com.cubetiq.blog.api.repository.PostRepository
import com.cubetiq.blog.api.repository.UserRepository
import com.cubetiq.blog.api.service.PostService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class PostServiceImpl @Autowired constructor(
    private val postRepository: PostRepository,
    private val categoryRepository: CategoryRepository,
    private val userRepository: UserRepository,
) : PostService {
    override fun create(request: PostRequest): PostEntity? {
        val category = categoryRepository.queryByIdAndDeletedAtIsNull(request.categoryId?.toLong()!!)
            ?: throw Exception("Category not found!")
        val user = userRepository.queryByIdAndDeletedAtIsNull(request.userId?.toLong()!!)
            ?: throw Exception("User not found!")

        val data = request.toEntity()
        data.category = category
        data.user = user

        return postRepository.save(data)
    }

    override fun update(id: Long, request: PostRequest): PostEntity? {
        val data = findById(id)
            ?: throw Exception("Post not found!")

        val category = categoryRepository.queryByIdAndDeletedAtIsNull(request.categoryId?.toLong()!!)
            ?: throw Exception("Category not found!")
        val user = userRepository.queryByIdAndDeletedAtIsNull(request.userId?.toLong()!!)
            ?: throw Exception("User not found!")

        data.title = request.title ?: data.title
        data.description = request.description ?: data.description
        data.attachment = request.attachment ?: data.attachment
        data.category = category
        data.user = user

        return postRepository.save(data)
    }

    override fun findById(id: Long): PostEntity? {
        return postRepository.queryByIdAndDeletedAtIsNull(id)
    }

    @Transactional
    override fun delete(id: Long): PostEntity? {
        val data = findById(id)
            ?: throw Exception("Post not found!")

        postRepository.deleteById(data.id!!)

        return data
    }

    override fun softDelete(id: Long): PostEntity? {
        val data = findById(id)
            ?: throw Exception("Post not found!")

        data.deletedAt = Date()

        return postRepository.save(data)
    }

    override fun findAllAvailable(): MutableList<PostEntity> {
        return postRepository.queryAllByDeletedAtIsNull()
    }

    override fun findAllAvailable(pageable: Pageable): Page<PostEntity> {
        return postRepository.queryAllByDeletedAtIsNull(pageable)
    }

    override fun findAllAvailable(categoryId: Long, pageable: Pageable): Page<PostEntity> {
        return postRepository.queryAllByCategoryIdAndDeletedAtIsNull(categoryId, pageable)
    }
}