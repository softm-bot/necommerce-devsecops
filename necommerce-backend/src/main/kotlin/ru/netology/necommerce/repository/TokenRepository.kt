package ru.netology.necommerce.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.netology.necommerce.entity.TokenEntity

interface TokenRepository : JpaRepository<TokenEntity, String>