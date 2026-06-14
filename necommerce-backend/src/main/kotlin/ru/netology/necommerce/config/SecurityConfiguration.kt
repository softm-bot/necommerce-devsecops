package ru.netology.necommerce.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder

@Configuration
class SecurityConfiguration {
    @Bean
    fun passwordEncoder(): PasswordEncoder = SCryptPasswordEncoder()
}