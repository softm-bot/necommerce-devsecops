package ru.netology.necommerce.controller

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import ru.netology.necommerce.dto.Product
import ru.netology.necommerce.service.ProductService

@RestController
@RequestMapping("/api/products")
class ProductController(private val service: ProductService) {
    @GetMapping
    fun getAll() = service.getAll()

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long) = service.getById(id)

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    fun save(@RequestBody dto: Product) = service.save(dto)

    @DeleteMapping("/{id}")
    fun removeById(@PathVariable id: Long) = service.removeById(id)

    @PostMapping("/{id}/likes")
    @PreAuthorize("hasRole('USER')")
    fun likeById(@PathVariable id: Long) = service.likeById(id)

    @DeleteMapping("/{id}/likes")
    @PreAuthorize("hasRole('USER')")
    fun unlikeById(@PathVariable id: Long) = service.unlikeById(id)
}