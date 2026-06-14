package ru.netology.necommerce.repository

import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import ru.netology.necommerce.entity.OrderEntity

interface OrderRepository : JpaRepository<OrderEntity, Long> {
    fun findAllByUserId(userId: Long, sort: Sort): List<OrderEntity>
}