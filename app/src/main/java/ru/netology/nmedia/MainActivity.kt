package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val displayingNumbers = DisplayingNumbers()

        val viewModel: PostViewModel by viewModels()
        with(binding){
            viewModel.data.observe(this@MainActivity){post ->
                author.text = post.author
                content.text = post.content
                published.text = post.published
                val image = if (post.likedByMe) {
                    R.drawable.ic_baseline_favorite_24
                } else {
                    R.drawable.ic_baseline_favorite_border_24
                }
                likes.setImageResource(image)
                likesSum.text = displayingNumbers.displaying(post.likesCount)

                likes.setOnClickListener {
                    viewModel.like()
                }
                repost.setOnClickListener {
                    viewModel.repost()
                }
                repostSum.text = displayingNumbers.displaying(post.repostSum)
            }

            }
        }
    }
