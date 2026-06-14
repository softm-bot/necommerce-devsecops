package ru.netology.necommerce.controller

import org.springframework.context.annotation.Profile
import org.springframework.web.bind.annotation.*
import ru.netology.necommerce.dto.PushMessage
import ru.netology.necommerce.service.PushService

@Profile("production")
@RestController
@RequestMapping("/api/pushes")
class PushController(private val service: PushService) {
    @PostMapping
    fun send(@RequestParam token: String, @RequestBody message: PushMessage) = service.send(token, message)
}