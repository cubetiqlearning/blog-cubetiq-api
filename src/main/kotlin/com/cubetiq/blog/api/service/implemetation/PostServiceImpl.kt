package com.cubetiq.blog.api.service.implemetation

import com.cubetiq.blog.api.exception.NotFoundException
import com.cubetiq.blog.api.model.entity.PostEntity
import com.cubetiq.blog.api.model.request.post.PostRequest
import com.cubetiq.blog.api.repository.CategoryRepository
import com.cubetiq.blog.api.repository.PostRepository
import com.cubetiq.blog.api.repository.UserRepository
import com.cubetiq.blog.api.service.PostService
import com.cubetiq.blog.api.service.UserAuthService
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
    private val userAuthService: UserAuthService,
) : PostService {
    override fun create(request: PostRequest): PostEntity? {
        val category = categoryRepository.queryByIdAndDeletedAtIsNull(request.categoryId?.toLong()!!)
            ?: throw NotFoundException("Category not found!")
        val user = userAuthService.getCurrentUser()

        val data = request.toEntity()
        data.category = category
        data.user = user

        return try {
            postRepository.save(data)
        } catch (ex: Exception) {
            throw Exception(ex)
        }
    }

    override fun update(id: Long, request: PostRequest): PostEntity? {
        val user = userAuthService.getCurrentUser()
        val data = findByIdAndUserId(id, user.id!!)
            ?: throw NotFoundException("Post not found!")

        val category = categoryRepository.queryByIdAndDeletedAtIsNull(request.categoryId?.toLong()!!)
            ?: throw NotFoundException("Category not found!")

        data.title = request.title ?: data.title
        data.description = request.description ?: data.description
        data.attachment = request.attachment ?: data.attachment
        data.category = category
        data.user = user

        return try {
            postRepository.save(data)
        } catch (ex: Exception) {
            throw Exception(ex)
        }
    }

    override fun findById(id: Long): PostEntity? {
        return postRepository.queryByIdAndDeletedAtIsNull(id)
    }

    override fun findByIdAndUserId(id: Long, userId: Long): PostEntity? {
        return postRepository.queryByIdAndUserIdAndDeletedAtIsNull(id, userId)
    }

    @Transactional
    override fun delete(id: Long): PostEntity? {
        val user = userAuthService.getCurrentUser()
        val data = findByIdAndUserId(id, user.id!!)
            ?: throw NotFoundException("Post not found!")

        try {
            postRepository.deleteById(data.id!!)
        } catch (ex: Exception) {
            throw Exception(ex)
        }

        return data
    }

    override fun softDelete(id: Long): PostEntity? {
        val user = userAuthService.getCurrentUser()
        val data = findByIdAndUserId(id, user.id!!)
            ?: throw NotFoundException("Post not found!")

        data.deletedAt = Date()

        return try {
            postRepository.save(data)
        } catch (ex: Exception) {
            throw Exception(ex)
        }
    }

    override fun findAllAvailable(): MutableList<PostEntity> {
        return postRepository.queryAllByDeletedAtIsNull()
    }

    override fun findAllAvailable(pageable: Pageable): Page<PostEntity> {
        return postRepository.queryAllByDeletedAtIsNull(pageable)
    }

    override fun findMyPosts(pageable: Pageable): Page<PostEntity> {
        val user = userAuthService.getCurrentUser()
        return postRepository.queryAllByUserIdAndDeletedAtIsNull(user.id!!, pageable)
    }

    override fun findAllAvailable(categoryId: Long, pageable: Pageable): Page<PostEntity> {
        return postRepository.queryAllByCategoryIdAndDeletedAtIsNull(categoryId, pageable)
    }
}