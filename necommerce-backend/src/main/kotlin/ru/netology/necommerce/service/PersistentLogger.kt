package ru.netology.necommerce.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.util.ResourceUtils
import java.nio.file.Files
import java.nio.file.StandardOpenOption
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

@Service
class PersistentLogger(@Value("\${app.log-location}") private val logLocation: String) {
    private val path = ResourceUtils.getFile(logLocation).toPath()

    fun log(message: String) {
        val now = OffsetDateTime.now()
        Files.write(
            path.resolve(now.format(DateTimeFormatter.ISO_OFFSET_DATE)),
            "${now.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)}: $message".toByteArray(),
            StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.APPEND,
        )
    }
}