package com.cubetiq.blog.api.model.entity

import com.cubetiq.blog.api.constant.TableConstant
import com.cubetiq.blog.api.infrastructure.model.entity.BaseEntity
import com.cubetiq.blog.api.module.security.UserAuthDetails
import com.fasterxml.jackson.annotation.JsonManagedReference
import org.hibernate.Hibernate
import javax.persistence.*

@Entity
@Table(name = TableConstant.USER)
open class UserEntity constructor(
    @Column(nullable = false, unique = true, length = 30)
    open var username: String? = null,

    @Column(nullable = false, length = 60)
    open var password: String? = null,

    @Column(nullable = false)
    open var enabled: Boolean? = true,

    @Column(nullable = false)
    open var enabledUser: Boolean? = true,

    @ManyToMany(
        fetch = FetchType.LAZY,
        cascade = [CascadeType.DETACH, CascadeType.REFRESH]
    )
    @JoinTable(
        name = "userRole",
        joinColumns = [JoinColumn(name = "userId")],
        inverseJoinColumns = [JoinColumn(name = "roleId")]
    )
    @JsonManagedReference
    open var roles: MutableList<RoleEntity>? = mutableListOf(),

    @OneToMany(
        fetch = FetchType.LAZY,
        cascade = [CascadeType.REFRESH, CascadeType.DETACH],
        mappedBy = "user"
    )
    @JsonManagedReference
    open var post: MutableList<PostEntity>? = mutableListOf(),
): BaseEntity<Long>() {
    fun getUserAuthDetails(): UserAuthDetails {
        return UserAuthDetails(this)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as UserEntity

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , createdBy = $createdBy , createdDate = $createdDate , updatedBy = $updatedBy , updatedDate = $updatedDate , deletedAt = $deletedAt , username = $username , password = $password , enabled = $enabled , enabledUser = $enabledUser )"
    }
}