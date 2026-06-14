package ru.netology.necommerce.dto

data class PushMessage(
    val recipientId: Long?,
    val content: String,
)
