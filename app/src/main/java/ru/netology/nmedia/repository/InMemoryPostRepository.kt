package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post

class InMemoryPostRepository: PostRepository {


    private var posts = List(1_000){
        Post(
            id = it.toLong(),
        author = "Нетология. Университет интернет-профессий будущего",
        published = "21 мая в 18:36",
        content = "$it Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            likedByMe = false
    )
    }.reversed()

    private val _data = MutableLiveData(posts)
    override fun likeById(id: Long) {
        posts = posts.map { post ->
            if (post.id == id ) {
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
                post.copy(repostSum = post.repostSum + 1 )
            } else {
                post
            }
        }
        _data.value = posts
    }

    override fun removeById(id: Long) {
        posts = posts.filter {
            it.id != id
        }
        _data.value = posts
    }

    override fun save(post: Post) {
        if (post.id == 0L){
            val newId = posts.firstOrNull()?.id?: post.id
            posts = listOf(post.copy(id = newId + 1)) + posts

            _data.value = posts
            return
        }
        posts = posts.map {
            if (it.id != post.id) it else it.copy(content = post.content)
        }
        _data.value = posts
    }

    override fun get(): LiveData<List<Post>> = _data
}