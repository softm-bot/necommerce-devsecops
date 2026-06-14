package ru.netology.necommerce.entity

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
data class TokenEntity(
    @Id
    var token: String,
    @ManyToOne
    var user: UserEntity,
)
