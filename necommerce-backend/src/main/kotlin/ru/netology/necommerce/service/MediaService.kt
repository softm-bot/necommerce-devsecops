package ru.netology.necommerce.service

import org.apache.tika.Tika
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.util.MimeTypeUtils
import org.springframework.util.ResourceUtils
import org.springframework.web.multipart.MultipartFile
import ru.netology.necommerce.dto.Media
import ru.netology.necommerce.exception.BadContentTypeException
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

@Service
class MediaService(@Value("\${app.media-location}") private val mediaLocation: String) {
    private val path = ResourceUtils.getFile(mediaLocation).toPath()
    private val tika = Tika()

    init {
        Files.createDirectories(path)
    }

    fun saveMedia(file: MultipartFile): Media = save(file)

    fun saveAvatar(file: MultipartFile): Media = save(file)

    fun save(file: MultipartFile): Media {
        val mediaType: String = tika.detect(file.inputStream)
        val id = UUID.randomUUID().toString() + when (mediaType) {
            MimeTypeUtils.IMAGE_JPEG_VALUE -> ".jpg"
            MimeTypeUtils.IMAGE_PNG_VALUE -> ".png"
            else -> throw BadContentTypeException()
        }
        file.transferTo(path.resolve(Paths.get(id)))
        return Media(id)
    }
}
