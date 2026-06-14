package ru.netology.necommerce.repository

import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import ru.netology.necommerce.entity.CommentEntity

interface CommentRepository : JpaRepository<CommentEntity, Long> {
    fun findAllByProductId(productId: Long, sort: Sort): List<CommentEntity>
    @Modifying
    fun removeAllByProductId(productId: Long)
}