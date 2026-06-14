package ru.netology.necommerce.service

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import ru.netology.necommerce.repository.TokenRepository
import javax.transaction.Transactional

@Service
@Transactional
class ScheduledTokenInvalidatorService(
    private val tokenRepository: TokenRepository,
) {
    @Scheduled(initialDelay = 60 * 1000, fixedRate = 60 * 60 * 1000)
    fun invalidate() {
        // TODO: implement invalidation
    }
}