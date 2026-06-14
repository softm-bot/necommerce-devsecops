package ru.netology.necommerce.service

import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.netology.necommerce.dto.Comment
import ru.netology.necommerce.dto.isAdmin
import ru.netology.necommerce.entity.CommentEntity
import ru.netology.necommerce.exception.NotFoundException
import ru.netology.necommerce.exception.PermissionDeniedException
import ru.netology.necommerce.extensions.principal
import ru.netology.necommerce.repository.CommentRepository
import java.time.OffsetDateTime

@Service
@Transactional
class CommentService(private val repository: CommentRepository) {
    fun getAllByPostId(postId: Long): List<Comment> {
        val principal = principal()
        return repository
            .findAllByProductId(postId, Sort.by(Sort.Direction.ASC, "id"))
            .map { it.toDto(principal.id) }
    }

    fun getById(id: Long): Comment {
        val principal = principal()
        return repository
            .findById(id)
            .orElseThrow(::NotFoundException)
            .toDto(principal.id)
    }

    fun save(dto: Comment): Comment {
        val principal = principal()
        return repository
            .findById(dto.id)
            .orElse(
                CommentEntity.fromDto(
                    dto.copy(
                        authorId = principal.id,
                        author = principal.name,
                        authorAvatar = principal.avatar,
                        likes = 0,
                        likedByMe = false,
                        published = OffsetDateTime.now().toEpochSecond()
                    )
                )
            )
            .let {
                if (it.author.id != principal.id) {
                    throw PermissionDeniedException()
                }

                it.content = dto.content
                if (it.id == 0L) repository.save(it)
                it
            }.toDto(principal.id)
    }

    fun removeById(id: Long): Unit {
        val principal = principal()
        repository.findById(id)
            .orElseThrow(::NotFoundException)
            .let {
                if (it.author.id != principal.id || principal.isAdmin()) {
                    throw PermissionDeniedException()
                }
                repository.delete(it)
                it
            }
    }

    fun likeById(id: Long): Comment {
        val principal = principal()
        return repository
            .findById(id)
            .orElseThrow(::NotFoundException)
            .apply {
                likeOwnerIds.add(principal.id)
            }
            .toDto(principal.id)
    }

    fun unlikeById(id: Long): Comment {
        val principal = principal()
        return repository
            .findById(id)
            .orElseThrow(::NotFoundException)
            .apply {
                likeOwnerIds.add(principal.id)
            }
            .toDto(principal.id)
    }

    fun removeAllByProductId(productId: Long): Unit = repository
        .removeAllByProductId(productId)
}