package com.cubetiq.blog.api.repository

import com.cubetiq.blog.api.model.entity.PostEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostRepository : JpaRepository<PostEntity, Long> {
    fun queryByIdAndDeletedAtIsNull(id: Long): PostEntity?

    fun queryByIdAndUserIdAndDeletedAtIsNull(id: Long, userId: Long): PostEntity?

    fun queryAllByUserIdAndDeletedAtIsNull(userId: Long, pageable: Pageable): Page<PostEntity>

    fun queryAllByDeletedAtIsNull(): MutableList<PostEntity>

    fun queryAllByDeletedAtIsNull(pageable: Pageable): Page<PostEntity>

    fun queryAllByCategoryIdAndDeletedAtIsNull(categoryId: Long, pageable: Pageable): Page<PostEntity>
}