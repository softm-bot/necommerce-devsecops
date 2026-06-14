package ru.netology.necommerce.controller

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import ru.netology.necommerce.dto.Product
import ru.netology.necommerce.enumeration.OrderStatus
import ru.netology.necommerce.service.OrderService
import ru.netology.necommerce.service.ProductService

@RestController
@RequestMapping("/api/orders")
class OrderController(private val service: OrderService) {
    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping
    fun getAll() = service.getAll()

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/my")
    fun getAllMy() = service.getAllMy()

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long) = service.getById(id)

    @PostMapping
    fun make(@RequestParam productId: Long, @RequestParam phone: String) = service.make(productId, phone)

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("/{id}/status")
    fun changeStatus(@PathVariable id: Long, @RequestParam status: OrderStatus) = service.changeStatus(id, status)
}