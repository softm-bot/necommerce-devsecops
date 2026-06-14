package ru.netology.necommerce.dto

data class User(
    val id: Long,
    val login: String,
    val name: String,
    val avatar: String,
    val authorities: List<String>,
) {
}

fun User.isAdmin() = authorities.contains("ROLE_ADMIN")
fun User.isAnonymous() = authorities.contains("ROLE_ANONYMOUS")

val AnonymousUser = User(
    id = -1L,
    login = "anonymous",
    name = "Anonymous",
    avatar = "netology.jpg",
    authorities = listOf("ROLE_ANONYMOUS")
)
