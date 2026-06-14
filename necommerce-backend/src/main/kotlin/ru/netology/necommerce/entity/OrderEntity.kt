package ru.netology.necommerce.entity

import ru.netology.necommerce.dto.AnonymousUser
import ru.netology.necommerce.dto.Order
import ru.netology.necommerce.enumeration.OrderStatus
import javax.persistence.*

@Entity
data class OrderEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    @ManyToOne
    var user: UserEntity?,
    var phone: String,
    val productId: Long,
    val productName: String,
    val productPrice: Int,
    var published: Long,
    @Enumerated(EnumType.STRING)
    var status: OrderStatus
) {
    fun toDto() = Order(id, user?.id ?: AnonymousUser.id, user?.name, phone, productId, productName, productPrice, published, status)

    companion object {
        fun fromDto(dto: Order) = OrderEntity(
            dto.id,
            if (dto.ownerId == AnonymousUser.id) null else UserEntity(dto.ownerId),
            dto.ownerPhone,
            dto.productId,
            dto.productName,
            dto.productPrice,
            dto.published,
            dto.status,
        )
    }
}