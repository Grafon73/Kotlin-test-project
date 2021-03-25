package com.kotlin.project.secondcontlinproject.entity

import org.springframework.data.util.ProxyUtils
import javax.persistence.*

@MappedSuperclass
abstract class BaseEntity<T> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Int? = null

    @Version
    open var version: Int? = null

    override fun equals(other: Any?): Boolean {
        other ?: return false

        if (this === other) return true

        if (javaClass != ProxyUtils.getUserClass(other)) return false

        other as BaseEntity<*>

        return this.id != null && this.id == other.id
    }

    override fun hashCode() = 25

    override fun toString(): String {
        return "${this.javaClass.simpleName}(id=$id)"
    }
}