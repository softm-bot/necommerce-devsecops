package ru.netology.necommerce.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.netology.necommerce.entity.UserEntity

interface UserRepository : JpaRepository<UserEntity, Long> {
    fun findByLogin(login: String?): UserEntity?
}