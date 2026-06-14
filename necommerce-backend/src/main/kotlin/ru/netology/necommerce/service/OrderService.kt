package ru.netology.necommerce.service

import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.netology.necommerce.dto.Order
import ru.netology.necommerce.dto.isAnonymous
import ru.netology.necommerce.entity.OrderEntity
import ru.netology.necommerce.entity.UserEntity
import ru.netology.necommerce.enumeration.OrderStatus
import ru.netology.necommerce.exception.NotFoundException
import ru.netology.necommerce.extensions.principal
import ru.netology.necommerce.repository.OrderRepository
import java.time.OffsetDateTime

@Service
@Transactional
class OrderService(
    private val repository: OrderRepository,
    private val productService: ProductService,
    private val persistentLogger: PersistentLogger,
) {
    fun getAll(): List<Order> {
        return repository
            .findAll(Sort.by(Sort.Direction.DESC, "id"))
            .map { it.toDto() }
    }

    fun getAllMy(): List<Order> {
        val principal = principal()
        return repository
            .findAllByUserId(principal.id, Sort.by(Sort.Direction.DESC, "id"))
            .map { it.toDto() }
    }

    fun getById(id: Long): Order {
        return repository
            .findById(id)
            .orElseThrow(::NotFoundException)
            .toDto()
    }

    fun make(productId: Long, phone: String): Order {
        val principal = principal()
        val product = productService.getById(productId)
        persistentLogger.log("$principal order $product")
        return repository.save(
            OrderEntity(
                0L,
                if (principal.isAnonymous()) null else UserEntity(principal.id),
                phone,
                product.id,
                product.name,
                product.price,
                OffsetDateTime.now().toEpochSecond(),
                OrderStatus.WAITING,
            )
        ).toDto()
    }

    fun changeStatus(id: Long, status: OrderStatus): Order {
        return repository
            .findById(id)
            .orElseThrow(::NotFoundException)
            .let {
                it.status = status
                it
            }.toDto()
    }
}