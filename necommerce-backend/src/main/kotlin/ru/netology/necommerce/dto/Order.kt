package ru.netology.necommerce.dto

import ru.netology.necommerce.enumeration.OrderStatus

data class Order(
    val id: Long,
    val ownerId: Long,
    val ownerName: String?,
    val ownerPhone: String,
    val productId: Long,
    val productName: String,
    val productPrice: Int,
    val published: Long,
    val status: OrderStatus,
)
