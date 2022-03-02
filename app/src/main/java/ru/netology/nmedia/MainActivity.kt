package ru.netology.nmedia

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.adapter.ClickListener
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.util.AndroidUtils
import ru.netology.nmedia.viewmodel.PostViewModel
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModel: PostViewModel by viewModels()
        val adapter = PostAdapter(
            object : ClickListener {
                override fun onLike(post: Post) {
                    viewModel.likeById(post.id)
                }

                override fun onRepost(post: Post) {
                    viewModel.repostById(post.id)
                }

                override fun onRemove(post: Post) {
                    viewModel.removeById(post.id)
                }

                override fun onEdit(post: Post) {
                    viewModel.edit(post)
                }
            }
        )

        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        viewModel.edited.observe(this) {
            if (it.id == 0L) {
                binding.deleteTextContent.visibility = View.GONE
                binding.textUndoChanges.visibility = View.GONE
                return@observe
            }
            with(binding) {
                deleteTextContent.visibility = View.VISIBLE
                textUndoChanges.visibility = View.VISIBLE
                editContent.requestFocus()
                deleteTextContent.setOnClickListener {
                    editContent.setText(viewModel.edited.value?.content)
                    viewModel.changeContent(editContent.text.toString())
                    viewModel.save()
                    editContent.setText("")
                    editContent.clearFocus()
                    AndroidUtils.hideKeyboard(binding.save)
                    deleteTextContent.visibility = View.GONE
                    textUndoChanges.visibility = View.GONE
                }
                editContent.setText(it.content)


            }
        }


        binding.save.setOnClickListener {
            with(binding.editContent) {
                if (text.isNullOrBlank()) {
                    Toast.makeText(this@MainActivity, "Content empty", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                } else {
                    viewModel.changeContent(text.toString())
                    viewModel.save()

                    setText("")
                    clearFocus()
                    AndroidUtils.hideKeyboard(this)
                }
            }

        }


    }
}
