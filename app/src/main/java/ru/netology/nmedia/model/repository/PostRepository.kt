package ru.netology.nmedia.model.repository

import androidx.lifecycle.LiveData

interface PostRepository {
    fun get(): LiveData<List<Post>>
    fun likeById(id: Long)
    fun repostById(id: Long)
    fun removeById(id: Long)
    fun save(post: Post)
    fun onPlayVideo(text: String, id: Long)

}