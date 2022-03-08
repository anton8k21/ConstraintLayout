package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.Post
import ru.netology.nmedia.adapter.ClickListener
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModel: PostViewModel by viewModels()

        val newPostContract = registerForActivityResult(NewPostActivity.Contract()){ result ->
            result?.let {
                viewModel.changeContent(result)
                viewModel.save()
            }

        }

        val adapter = PostAdapter(
            object : ClickListener {
                override fun onLike(post: Post) {
                    viewModel.likeById(post.id)
                }

                override fun onRepost(post: Post) {
                    val intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, post.content)
                    }
                    val repostIntent = Intent.createChooser(intent,"Поделиться")
                    startActivity(repostIntent)
                    viewModel.repostById(post.id)
                }

                override fun onRemove(post: Post) {
                    viewModel.removeById(post.id)
                }

                override fun onEdit(post: Post) {
                    viewModel.edit(post)
                }

                override fun onPlayVideo(url: String, id: Long) {
                    viewModel.onPlayVideo(url, id)
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    val videoIntent = Intent.createChooser(intent,"")
                    startActivity(videoIntent)
                }

            }
        )

        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }
        binding.add.setOnClickListener {
            newPostContract.launch("")
        }

        viewModel.edited.observe(this) {
            if (it.id == 0L) {
                return@observe
            }
            newPostContract.launch(it.content)
        }

    }

    }

