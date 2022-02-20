package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post

class InMemoryPostRepository: PostRepository {


    val post = Post(
        author = "Нетология. Университет интернет-профессий будущего",
        published = "21 мая в 18:36",
        content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb"
    )

    private val _data = MutableLiveData(post)
    override fun like() {
        val post = _data.value?: return
        _data.value = _data.value?.copy(
            likesCount = if (!post.likedByMe) post.likesCount + 1 else post.likesCount - 1,
            likedByMe = !post.likedByMe

        )
    }

    override fun repost() {
        val post = _data.value?: return
        _data.value = _data.value?.copy(
            repostSum = post.repostSum + 1
        )
    }

    override val data: LiveData<Post>
        get() = _data
}