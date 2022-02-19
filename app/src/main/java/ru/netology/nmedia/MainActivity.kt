package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import ru.netology.nmedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val displayingNumbers = DisplayingNumbers()


        val post = Post(
            author = "Нетология. Университет интернет-профессий будущего",
            published = "21 мая в 18:36",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb"
        )

        binding.likesSum.text = displayingNumbers.displaying(post.likesCount)
        binding.likes.setOnClickListener {
            post.likedByMe = !post.likedByMe
            post.likesCount = if (post.likedByMe) {
                ++post.likesCount
            } else {
                --post.likesCount
            }


            val image = if (post.likedByMe) {
                R.drawable.ic_baseline_favorite_24
            } else {
                R.drawable.ic_baseline_favorite_border_24
            }
            binding.likes.setImageResource(image)
            binding.likesSum.text = displayingNumbers.displaying(post.likesCount)
        }

        binding.repostSum.text = displayingNumbers.displaying(post.repostSum)
        binding.repost.setOnClickListener {
            ++post.repostSum
            binding.repostSum.text = displayingNumbers.displaying(post.repostSum)
        }
        val content = findViewById<TextView>(R.id.content)
        content.text = post.content
        val author = findViewById<TextView>(R.id.author)
        author.text = post.author
        val publoshed = findViewById<TextView>(R.id.published)
        publoshed.text = post.published

    }
}