package ru.netology.necommerce.controller

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import ru.netology.necommerce.dto.Comment
import ru.netology.necommerce.service.CommentService

@RestController
@RequestMapping("/api/products/{productId}/comments")
class CommentController(private val service: CommentService) {
    @GetMapping
    fun getAllByProductId(@PathVariable productId: Long) = service.getAllByPostId(productId)

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    fun save(@RequestBody dto: Comment) = service.save(dto)

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    fun deleteById(@PathVariable id: Long) = service.removeById(id)

    @PostMapping("/{id}/likes")
    @PreAuthorize("hasRole('USER')")
    fun likeById(@PathVariable id: Long) = service.likeById(id)

    @DeleteMapping("/{id}/likes")
    @PreAuthorize("hasRole('USER')")
    fun unlikeById(@PathVariable id: Long) = service.unlikeById(id)
}