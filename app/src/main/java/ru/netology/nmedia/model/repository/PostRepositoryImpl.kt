package ru.netology.nmedia.model.repository

import android.view.animation.Transformation
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import ru.netology.nmedia.model.dao.PostDao
import ru.netology.nmedia.model.dao.PostEntity

class PostRepositoryImpl(
   private val dao: PostDao
): PostRepository {
    override fun get() = Transformations.map(dao.getAll()){list ->
        list.map {
            Post(it.id, it.author, it.urlVideo, it.published, it.content, it.likedByMe, it.likesCount, it.repostSum)
        }
    }

    override fun likeById(id: Long) {
        dao.likedById(id)
    }

    override fun repostById(id: Long) {
        dao.repostById(id)
    }

    override fun onPlayVideo(text: String, id: Long) {
       dao.onPlayVideo(text, id)
    }



    override fun removeById(id: Long) {
       dao.removById(id)
    }

    override fun save(post: Post) {
       dao.save(PostEntity.fromDto(post))
    }
}