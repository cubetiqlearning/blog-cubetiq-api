package com.cubetiq.blog.api.model.entity

import com.cubetiq.blog.api.constant.TableConstant
import com.cubetiq.blog.api.infrastructure.converter.MapConverter
import com.cubetiq.blog.api.infrastructure.model.entity.BaseEntity
import org.hibernate.Hibernate
import javax.persistence.Column
import javax.persistence.Convert
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = TableConstant.CONFIGURATION)
open class ConfigurationEntity constructor(
    @Column(length = 30, nullable = false)
    open var key: String? = null,

    @Convert(converter = MapConverter::class)
    @Column(columnDefinition = "TEXT")
    open var value: Map<String, Any?>? = mapOf(),
) : BaseEntity<Long>() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as ConfigurationEntity

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , createdBy = $createdBy , createdDate = $createdDate , updatedBy = $updatedBy , updatedDate = $updatedDate , deletedAt = $deletedAt , key = $key , value = $value )"
    }
}