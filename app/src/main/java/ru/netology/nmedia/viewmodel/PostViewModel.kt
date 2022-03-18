package ru.netology.nmedia.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.model.db.AppDb
import ru.netology.nmedia.model.repository.Post
import ru.netology.nmedia.model.repository.PostRepository
import ru.netology.nmedia.model.repository.PostRepositoryImpl


val empty = Post(
    id = 0,
    author = "",
    urlVideo = "",
    published = "",
    content = "",
    likedByMe = false,
    likesCount = 0,
    repostSum = 0
)

class PostViewModel(application: Application): AndroidViewModel(application) {

    private val repository: PostRepository = PostRepositoryImpl(AppDb.getInstance(context = application).postDao())

    fun likeById(id: Long) = repository.likeById(id)

    fun repostById(id: Long) = repository.repostById(id)

    fun removeById(id: Long) = repository.removeById(id)

    fun onPlayVideo(text: String, id: Long) = repository.onPlayVideo(text,id)

    val data = repository.get()

    val edited = MutableLiveData(empty)

    fun edit(post: Post){
        edited.value = post
    }

    fun save(){
        edited.value?.let{
            repository.save(it)
            edited.value = empty

        }
    }
    fun changeContent(content: String){
        edited.value?.let{
            if (it.content == content){
                return
            }else
                edited.value = it.copy(content = content)
        }
    }
}