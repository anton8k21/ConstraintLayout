package ru.netology.nmedia.viewmodel

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.repository.InMemoryPostRepository
import ru.netology.nmedia.repository.PostRepository

class PostViewModel: ViewModel() {

    private val repository: PostRepository = InMemoryPostRepository()

    fun likeById(id: Long) = repository.likeById(id)

    fun repostById(id: Long) = repository.repostById(id)

    val data = repository.get()
}