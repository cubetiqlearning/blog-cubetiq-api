package com.cubetiq.blog.api.model.entity

import com.cubetiq.blog.api.constant.TableConstant
import com.cubetiq.blog.api.infrastructure.model.entity.BaseEntity
import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.Hibernate
import javax.persistence.*

@Entity
@Table(name = TableConstant.CATEGORY)
open class CategoryEntity constructor(
    @Column(length = 30, nullable = false)
    open var name: String? = null,

    @Column(length = 50)
    open var notes: String? = null,

    @OneToMany(
        fetch = FetchType.LAZY,
        cascade = [CascadeType.DETACH, CascadeType.REFRESH],
        mappedBy = "category"
    )
    @JsonIgnore
    open var post: MutableList<PostEntity>? = mutableListOf(),
): BaseEntity<Long>() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as CategoryEntity

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , createdBy = $createdBy , createdDate = $createdDate , updatedBy = $updatedBy , updatedDate = $updatedDate , deletedAt = $deletedAt , name = $name , notes = $notes )"
    }
}