package ru.netology.necommerce.service

import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.netology.necommerce.dto.Product
import ru.netology.necommerce.entity.ProductEntity
import ru.netology.necommerce.exception.NotFoundException
import ru.netology.necommerce.exception.PermissionDeniedException
import ru.netology.necommerce.extensions.principal
import ru.netology.necommerce.repository.ProductRepository
import java.time.OffsetDateTime

@Service
@Transactional
class ProductService(
    private val repository: ProductRepository,
    private val commentService: CommentService,
) {
    fun getAll(): List<Product> {
        val principal = principal()
        return repository
            .findAll(Sort.by(Sort.Direction.DESC, "id"))
            .map { it.toDto(principal.id) }
    }

    fun getById(id: Long): Product {
        val principal = principal()
        return repository
            .findById(id)
            .orElseThrow(::NotFoundException)
            .toDto(principal.id)
    }

    fun save(dto: Product): Product {
        val principal = principal()
        return repository
            .findById(dto.id)
            .orElse(
                ProductEntity.fromDto(
                    dto.copy(
                        likes = 0,
                        likedByMe = false,
                        published = OffsetDateTime.now().toEpochSecond()
                    )
                )
            )
            .let {
                it.name = dto.name
                it.content = dto.content
                it.price = dto.price
                if (it.id == 0L) repository.save(it)
                it
            }.toDto(principal.id)
    }

    fun removeById(id: Long): Unit {
        repository.findById(id)
            .orElseThrow(::NotFoundException)
            .let {
                repository.delete(it)
                it
            }
            .also {
                commentService.removeAllByProductId(it.id)
            }
    }

    fun likeById(id: Long): Product {
        val principal = principal()
        return repository
            .findById(id)
            .orElseThrow(::NotFoundException)
            .apply {
                likeOwnerIds.add(principal.id)
            }
            .toDto(principal.id)
    }

    fun unlikeById(id: Long): Product {
        val principal = principal()
        return repository
            .findById(id)
            .orElseThrow(::NotFoundException)
            .apply {
                likeOwnerIds.remove(principal.id)
            }
            .toDto(principal.id)
    }

    fun saveInitial(dto: Product) = ProductEntity.fromDto(
        dto.copy(
            likes = 0,
            likedByMe = false,
            published = OffsetDateTime.now().toEpochSecond()
        )
    ).let {
        it.content = dto.content
        repository.save(it)
        it
    }.toDto(0L)
}