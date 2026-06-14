package ru.netology.necommerce.dto

import ru.netology.necommerce.enumeration.AttachmentType

data class Product(
    val id: Long,
    val name: String,
    val content: String,
    val price: Int,
    val published: Long,
    val likedByMe: Boolean,
    val likes: Int = 0,
    var attachment: Attachment? = null,
)

data class Attachment(
    val url: String,
    val type: AttachmentType,
)
