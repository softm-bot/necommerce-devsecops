package ru.netology.necommerce.controller

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import ru.netology.necommerce.dto.PushToken
import ru.netology.necommerce.service.UserService

@RestController
@RequestMapping("/api/users")
class UserController(private val service: UserService) {
    @PostMapping("/registration")
    fun register(
        @RequestParam login: String,
        @RequestParam pass: String,
        @RequestParam name: String,
        @RequestParam(required = false) file: MultipartFile?,
    ) = service.register(login = login, pass = pass, name = name, file = file)

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/creation")
    fun register(
        @RequestParam login: String,
        @RequestParam pass: String,
        @RequestParam name: String,
        @RequestParam avatar: String,
        @RequestParam roles: Collection<String>,
    ) = service.create(login = login, pass = pass, name = name, avatar = avatar, roles = roles)

    @PostMapping("/authentication")
    fun login(@RequestParam login: String, @RequestParam pass: String) = service.login(login, pass)

    @PostMapping("/push-tokens")
    fun saveToken(@RequestBody pushToken: PushToken) = service.saveToken(pushToken)
}