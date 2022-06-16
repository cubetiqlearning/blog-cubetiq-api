package com.cubetiq.blog.api.infrastructure.model.entity

import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import java.io.Serializable
import java.util.*
import javax.persistence.*

@MappedSuperclass
abstract class BaseEntity<ID: Serializable> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: ID? = null

    @CreatedBy
    @Column(length = 30)
    open var createdBy: String? = null

    @CreatedDate
    open var createdDate: Date? = null

    @LastModifiedBy
    @Column(length = 30)
    open var updatedBy: String? = null

    @LastModifiedDate
    open var updatedDate: Date? = null

    @Column
    open var deletedAt: Date? = null
}