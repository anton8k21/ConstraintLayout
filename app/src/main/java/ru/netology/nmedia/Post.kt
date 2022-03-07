package ru.netology.nmedia

data class Post(
    var id: Long = 0L,
    val author: String,
    val urlVideo: String,
    val published: String,
    val content: String,
    val likedByMe: Boolean = false,
    val likesCount: Int = 0,
    val repostSum: Int = 0
) {
}