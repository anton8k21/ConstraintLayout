package ru.netology.nmedia.dao

import androidx.lifecycle.LiveData
import ru.netology.nmedia.Post

interface PostDao {
    fun get(): List<Post>
    fun likeById(id: Long)
    fun repostById(id: Long)
    fun removeById(id: Long)
    fun save(post: Post): Post
    fun onPlayVideo(text: String, id: Long)
}