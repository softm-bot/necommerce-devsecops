package ru.netology.necommerce.controller

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import ru.netology.necommerce.service.MediaService

@RestController
@RequestMapping("/api/avatars")
class AvatarController(private val service: MediaService) {
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    fun save(@RequestParam file: MultipartFile) = service.saveAvatar(file)
}