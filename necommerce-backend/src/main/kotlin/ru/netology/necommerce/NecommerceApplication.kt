package ru.netology.necommerce

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.util.ResourceUtils
import ru.netology.necommerce.dto.Attachment
import ru.netology.necommerce.dto.Product
import ru.netology.necommerce.enumeration.AttachmentType
import ru.netology.necommerce.service.ProductService
import ru.netology.necommerce.service.UserService

@EnableScheduling
@SpringBootApplication
class NMediaApplication {
    @Bean
    fun runner(
        userService: UserService,
        productService: ProductService,
//        @Value("\${app.media-location}") mediaLocation: String,
    ) = CommandLineRunner {
//        ResourceUtils.getFile("classpath:static").copyRecursively(
//            ResourceUtils.getFile(mediaLocation),
//            true,
//        )
        userService.createAdminIfNotExists()
        productService.saveInitial(Product(
            0L,
            "Диджитал-печеньки",
            "Получите вдохновляющее предсказание или выиграйте приз",
            100,
            0L,
            false,
            0,
            Attachment("8sOzHlxFZMw.jpg", AttachmentType.IMAGE),
        ))
        productService.saveInitial(Product(
            0L,
            "Подборка книг",
            "Книги, которые могут пережить карьерный кризис",
            5_000,
            0L,
            false,
            0,
            Attachment("ArWZdhZiVvg.jpg", AttachmentType.IMAGE),
        ))
        productService.saveInitial(Product(
            0L,
            "Подборка книг",
            "Как решиться на перемены: подборка книг",
            4_000,
            0L,
            false,
            0,
            Attachment("E9Qgkyi3M18.jpg", AttachmentType.IMAGE),
        ))
        productService.saveInitial(Product(
            0L,
            "Подборка книг",
            "Подборка книг о лидерстве и развитии лидерских качеств",
            5_000,
            0L,
            false,
            0,
            Attachment("Grq3aLUnUf8.jpg", AttachmentType.IMAGE),
        ))
        productService.saveInitial(Product(
            0L,
            "Подборка фильмов",
            "5 фильмов, вдохновляющих на перемены в жизни",
            2_000,
            0L,
            false,
            0,
            Attachment("qCDOGOibdYE.jpg", AttachmentType.IMAGE),
        ))
        productService.saveInitial(Product(
            0L,
            "Подборка статей",
            "Как продвигать интернет-магазин: подборка статей",
            500,
            0L,
            false,
            0,
            Attachment("Z6CvYqpzmSc.jpg", AttachmentType.IMAGE),
        ))
    }
}

fun main(args: Array<String>) {
    runApplication<NMediaApplication>(*args)
}
