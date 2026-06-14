package ru.netology.necommerce.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import ru.netology.necommerce.dto.PushMessage
import ru.netology.necommerce.repository.PushTokenRepository
import javax.transaction.Transactional

@Profile("production")
@Service
@Transactional
class PushService(
    private val messaging: FirebaseMessaging,
    private val pushTokenRepository: PushTokenRepository,
    private val objectMapper: ObjectMapper,
) {
    fun send(token: String, message: PushMessage) {
        messaging.send(
            Message.builder()
                .putData("content", objectMapper.writeValueAsString(message))
                .setToken(token)
                .build()
        )
    }
}