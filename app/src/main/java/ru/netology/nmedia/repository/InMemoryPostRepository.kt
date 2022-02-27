package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post

class InMemoryPostRepository: PostRepository {


    private var post = List(1_000){
        Post(
            id = it.toLong(),
        author = "Нетология. Университет интернет-профессий будущего",
        published = "21 мая в 18:36",
        content = "$it Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            likedByMe = false
    )
    }.reversed()

    private val _data = MutableLiveData(post)
    override fun likeById(id: Long) {
        post = post.map {post ->
            if (post.id == id ) {
                    post.copy(likedByMe = !post.likedByMe, likesCount = if(post.likedByMe) post.likesCount - 1 else post.likesCount + 1)
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