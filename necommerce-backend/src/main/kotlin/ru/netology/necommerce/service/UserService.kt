package ru.netology.necommerce.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import ru.netology.necommerce.dto.PushToken
import ru.netology.necommerce.dto.Token
import ru.netology.necommerce.dto.User
import ru.netology.necommerce.entity.PushTokenEntity
import ru.netology.necommerce.entity.TokenEntity
import ru.netology.necommerce.entity.UserEntity
import ru.netology.necommerce.exception.NotFoundException
import ru.netology.necommerce.exception.PasswordNotMatchException
import ru.netology.necommerce.extensions.principalOrNull
import ru.netology.necommerce.repository.PushTokenRepository
import ru.netology.necommerce.repository.TokenRepository
import ru.netology.necommerce.repository.UserRepository
import java.security.SecureRandom
import java.util.*
import javax.transaction.Transactional

@Service
@Transactional
class UserService(
    private val userRepository: UserRepository,
    private val tokenRepository: TokenRepository,
    private val pushTokenRepository: PushTokenRepository,
    private val passwordEncoder: PasswordEncoder,
    private val mediaService: MediaService,
) : UserDetailsService {
    private val logger: Logger = LoggerFactory.getLogger(UserService::class.java)
    private val random: Random = Random(9999)

    fun create(login: String, pass: String, name: String, roles: Collection<String> = listOf("ROLE_USER"), avatar: String): User = userRepository.save(
        UserEntity(
            0L,
            login,
            passwordEncoder.encode(pass),
            name,
            roles,
            avatar,
        )
    ).toDto()

    fun register(login: String, pass: String, name: String, roles: Collection<String> = listOf("ROLE_USER"), file: MultipartFile?): Token {
        val avatar = file?.let {
            mediaService.saveAvatar(it)
        }

        return userRepository.save(
            UserEntity(
                0L,
                login,
                passwordEncoder.encode(pass),
                name,
                roles,
                avatar?.id ?: "netology.jpg",
            )
        ).let { user ->
            val token = Token(user.id, generateToken())
            tokenRepository.save(TokenEntity(token.token, user))
            token
        }
    }

    fun login(login: String, pass: String): Token = userRepository
        .findByLogin(login)
        ?.let { user ->
            if (!passwordEncoder.matches(pass, user.password)) {
                throw PasswordNotMatchException()
            }
            val token = Token(user.id, generateToken())
            tokenRepository.save(TokenEntity(token.token, user))
            token
        } ?: throw NotFoundException()

    fun getByLogin(login: String): User = userRepository
        .findByLogin(login)
        ?.toDto() ?: throw NotFoundException()

    fun getByToken(token: String): User = tokenRepository
        .findByIdOrNull(token)
        ?.user
        ?.toDto() ?: throw NotFoundException()

    override fun loadUserByUsername(username: String?): UserDetails =
        userRepository.findByLogin(username) ?: throw UsernameNotFoundException(username)

    private fun generateToken(length: Int = 128): String = ByteArray(length).apply {
        // lol
        random.nextBytes(this)
    }.let {
        Base64.getUrlEncoder().withoutPadding().encodeToString(it)
    }

    fun saveToken(pushToken: PushToken) {
        val userId = principalOrNull()?.id ?: 0
        pushTokenRepository.findByToken(pushToken.token)
            .orElse(PushTokenEntity(0, pushToken.token, userId))
            .let {
                if (it.id == 0L) pushTokenRepository.save(it) else it.userId = userId
            }
    }

    fun createAdminIfNotExists() {
        if (!userRepository.existsById(1L)) {
            val pass = generateToken(64)
            userRepository.save(
                UserEntity(
                    1L,
                    "admin",
                    passwordEncoder.encode(pass),
                    "Administrator",
                    listOf("ROLE_ADMIN"),
                    "netology.jpg",
                )
            ).also {
                logger.warn("admin user generated with login: ${it.login}, password: ${pass}")
            }
        }
    }
}