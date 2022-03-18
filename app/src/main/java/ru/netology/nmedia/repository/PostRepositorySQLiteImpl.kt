package ru.netology.nmedia.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post
import ru.netology.nmedia.dao.PostDao

class PostRepositorySQLiteImpl(
   private val dao: PostDao
): PostRepository {
    private var posts = emptyList<Post>()
    private val _data = MutableLiveData(posts)
    init {
        posts = dao.get()
        _data.value = posts
    }


    override fun likeById(id: Long) {
        posts = posts.map { post ->
            if (post.id == id ) {
                dao.likeById(id)
                    post.copy(likedByMe = !post.likedByMe, likesCount = if(post.likedByMe) post.likesCount - 1 else post.likesCount + 1)
                } else {
                    post

            }
        }
        _data.value = posts
    }

    override fun repostById(id: Long) {
        posts = posts.map { post ->
            if (post.id == id) {
                dao.repostById(id)
                post.copy(repostSum = post.repostSum + 1 )
            } else {
                post
            }
        }
        _data.value = posts
    }

    override fun onPlayVideo(text: String, id: Long) {
        posts = posts.map { post ->
            if (post.id == id) {
                dao.onPlayVideo(text,id)
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
        dao.removeById(id)
        _data.value = posts
    }

    override fun save(post: Post) {
        val id = post.id
        val saved = dao.save(post)
        posts = if (id == 0L){
            listOf(saved) + posts
        }else{
            posts.map {
                if (it.id != id) it else saved
            }
        }
        _data.value = posts
    }

    override fun get(): LiveData<List<Post>> = _data
}