package ru.netology.nmedia.model.dao

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.nmedia.model.repository.Post

@Entity
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    val author: String,
    val urlVideo: String,
    val published: String,
    val content: String,
    val likedByMe: Boolean = false,
    val likesCount: Int = 0,
    val repostSum: Int = 0
) {
    fun toDto() = Post(id, author, urlVideo, published, content, likedByMe, likesCount, repostSum)

    companion object {
        fun fromDto(dto: Post) =
            PostEntity(dto.id, dto.author, dto.urlVideo, dto.published, dto.content, dto.likedByMe, dto.likesCount, dto.repostSum)

    }
}