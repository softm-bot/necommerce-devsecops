package ru.netology.necommerce.repository

import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import ru.netology.necommerce.entity.ProductEntity

interface ProductRepository : JpaRepository<ProductEntity, Long> {
    fun findAllByIdGreaterThan(id: Long, sort: Sort): List<ProductEntity>
}