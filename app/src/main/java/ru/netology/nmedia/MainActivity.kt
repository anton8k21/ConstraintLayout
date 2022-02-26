package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import ru.netology.nmedia.adapter.LikeClickListener
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val displayingNumbers = DisplayingNumbers()

        val viewModel: PostViewModel by viewModels()
        val adapter = PostAdapter(){
            viewModel.likeById(it.id)
            viewModel.repostById(it.id)
        }

        binding.root.adapter = adapter
            viewModel.data.observe(this@MainActivity){posts ->
                adapter.posts = posts

            }


        }
    }
