package ru.netology.necommerce.entity

import ru.netology.necommerce.dto.Attachment
import ru.netology.necommerce.dto.Product
import ru.netology.necommerce.enumeration.AttachmentType
import java.time.OffsetDateTime
import javax.persistence.*

@Entity
data class ProductEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    var name: String,
    @Column(columnDefinition = "TEXT")
    var content: String,
    var price: Int,
    var published: Long,
    @ElementCollection
    var likeOwnerIds: MutableSet<Long> = mutableSetOf(),
    @Embedded
    var attachment: AttachmentEmbeddable?,
) {
    constructor(id: Long) : this(id, "", "", 0, OffsetDateTime.now().toEpochSecond(), mutableSetOf(), null)

    fun toDto(myId: Long) = Product(
        id,
        name,
        content,
        price,
        published,
        likeOwnerIds.contains(myId),
        likeOwnerIds.size,
        attachment?.toDto()
    )

    companion object {
        fun fromDto(dto: Product) = ProductEntity(
            dto.id,
            dto.name,
            dto.content,
            dto.price,
            dto.published,
            mutableSetOf(),
            AttachmentEmbeddable.fromDto(dto.attachment),
        )
    }
}

@Embeddable
data class AttachmentEmbeddable(
    var url: String,
    @Enumerated(EnumType.STRING)
    var type: AttachmentType,
) {
    fun toDto() = Attachment(url, type)

    companion object {
        fun fromDto(dto: Attachment?) = dto?.let {
            AttachmentEmbeddable(it.url, it.type)
        }
    }
}
