package ru.netology.nmedia.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.Post
import ru.netology.nmedia.databinding.PostFragmentBinding

class InMemoryPostRepository(
    private val context: Context
): PostRepository {
    private val gson = Gson()
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type
    private val fileName = "posts.json"
    private val nextId = 1L
    private var posts = emptyList<Post>()
    private val _data = MutableLiveData(posts)

    init {
        val file = context.filesDir.resolve(fileName)
        if (file.exists()){
            context.openFileInput(fileName).bufferedReader().use {
                posts = gson.fromJson(it,type)
                _data.value = posts
            }
        }else{
            sync()
        }
    }

    private fun sync(){
        context.openFileOutput(fileName, Context.MODE_PRIVATE).bufferedWriter().use {
            it.write(gson.toJson(posts))
        }
    }

    override fun likeById(id: Long) {
        posts = posts.map { post ->
            if (post.id == id ) {
                    post.copy(likedByMe = !post.likedByMe, likesCount = if(post.likedByMe) post.likesCount - 1 else post.likesCount + 1)
                } else {
                    post

            }
        }
        _data.value = posts
        sync()
    }

    override fun repostById(id: Long) {
        posts = posts.map { post ->
            if (post.id == id) {
                post.copy(repostSum = post.repostSum + 1 )
            } else {
                post
            }
        }
        _data.value = posts
        sync()
    }

    override fun onPlayVideo(text: String, id: Long) {
        posts = posts.map { post ->
            if (post.id == id) {
                post.copy(urlVideo = text)
            } else {
                post
            }
        }
    }



    override fun removeById(id: Long) {
        posts = posts.filter {
            it.id != id
        }
        _data.value = posts
        sync()
    }

    override fun save(post: Post) {
        if (post.id == 0L){
            val newId = posts.firstOrNull()?.id?: post.id
            posts = listOf(post.copy(id = newId + 1)) + posts

            _data.value = posts
            sync()
            return
        }
        posts = posts.map {
            if (it.id != post.id) it else it.copy(content = post.content)
        }
        _data.value = posts
        sync()
    }

    override fun get(): LiveData<List<Post>> = _data
}