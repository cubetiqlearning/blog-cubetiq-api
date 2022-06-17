package com.cubetiq.blog.api.model.entity

import com.cubetiq.blog.api.constant.TableConstant
import com.cubetiq.blog.api.infrastructure.model.entity.BaseEntity
import org.hibernate.Hibernate
import javax.persistence.*

@Entity
@Table(name = TableConstant.POST)
class PostEntity(
    @Column(nullable = false, length = 50)
    open var title: String? = null,

    @Basic
    open var description: String? = null,

    @Basic
    open var attachment: String? = null,

    @ManyToOne(
        fetch = FetchType.LAZY,
        cascade = [CascadeType.DETACH, CascadeType.REFRESH]
    )
    @JoinColumn(name = "categoryId")
    open var category: CategoryEntity? = null,

    @ManyToOne(
        fetch = FetchType.LAZY,
        cascade = [CascadeType.DETACH, CascadeType.REFRESH]
    )
    @JoinColumn(name = "userId")
    open var user: UserEntity? = null,
): BaseEntity<Long>() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as PostEntity

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , createdBy = $createdBy , createdDate = $createdDate , updatedBy = $updatedBy , updatedDate = $updatedDate , deletedAt = $deletedAt , title = $title , description = $description , attachment = $attachment )"
    }
}