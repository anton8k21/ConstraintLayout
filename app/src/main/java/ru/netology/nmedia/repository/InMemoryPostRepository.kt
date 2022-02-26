package ru.netology.nmedia.repository

import android.view.LayoutInflater
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post
import ru.netology.nmedia.databinding.ActivityMainBinding

class InMemoryPostRepository: PostRepository {


    private var post = listOf(
        Post(
            id = 2,
        author = "Нетология. Университет интернет-профессий будущего",
        published = "21 мая в 18:36",
        content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            likedByMe = false
    ),
        Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            published = "21 мая в 18:36",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            likedByMe = false
        )
    )

    private val _data = MutableLiveData(post)
    override fun likeById(id: Long) {
        post = post.map {post ->
            if (post.id == id ) {
                post.copy(likedByMe = !post.likedByMe)
            } else {
                post
            }
        }
        _data.value = post
    }

    override fun repostById(id: Long) {
        post = post.map { post ->
            if (post.id == id) {
                post.copy(repostSum = post.repostSum + 1 )
            } else {
                post
            }
        }
        _data.value = post
    }


    override fun get(): LiveData<List<Post>> = _data
}