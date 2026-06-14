package ru.netology.necommerce.extensions

import org.springframework.security.core.context.SecurityContextHolder
import ru.netology.necommerce.dto.User

fun principal() = SecurityContextHolder.getContext().authentication.principal as User
fun principalOrNull() = SecurityContextHolder.getContext()?.authentication?.principal as? User?
