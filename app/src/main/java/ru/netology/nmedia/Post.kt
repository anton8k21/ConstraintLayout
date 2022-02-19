package ru.netology.nmedia

data class Post(
    val id: Long = 0L,
    val author: String,
    val published: String,
    val content: String,
    var likedByMe: Boolean = false,
    var likesCount: Int = 1099999,
    var repostSum: Int = 1099
) {
}