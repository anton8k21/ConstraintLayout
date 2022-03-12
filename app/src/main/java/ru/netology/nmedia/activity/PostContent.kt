package ru.netology.nmedia.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.PostFragmentBinding
import ru.netology.nmedia.util.LongArg
import ru.netology.nmedia.viewmodel.PostViewModel

class PostContent: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = PostFragmentBinding.inflate(layoutInflater)
        val viewModel: PostViewModel by viewModels(::requireParentFragment)

        val postId = arguments?.longArg?: -1
        viewModel.data.observe(viewLifecycleOwner){posts ->
            val post = posts.find { it.id == postId } ?: return@observe
            with(binding.cardPostFragment){
                if (post.urlVideo == ""){
                    postVideo.visibility = View.GONE
                }else
                    postVideo.visibility = View.VISIBLE
                author.text = post.author
                content.text = post.content
                published.text = post.published
                urlVideo.setText(post.urlVideo)
                repost.text = post.repostSum.toString()
                likes.isChecked = post.likedByMe
                likes.text = post.likesCount.toString()

               likes.setOnClickListener {
                   viewModel.likeById(postId)
               }
                repost.setOnClickListener {
                    val intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, post.content)
                    }
                    val repostIntent = Intent.createChooser(intent, "Поделиться")
                    startActivity(repostIntent)
                    viewModel.repostById(post.id)
                }
                menu.setOnClickListener {
                    PopupMenu(binding.root.context,menu).apply {
                        inflate(R.menu.post_menu)
                        setOnMenuItemClickListener {
                            when(it.itemId){
                                R.id.remove -> {
                                    viewModel.removeById(postId)
                                    findNavController().navigateUp()
                                    true
                                }
                                R.id.edit -> {
                                    viewModel.edit(post)
                                    findNavController().navigateUp()
                                    true
                                }
                                else -> false
                            }
                        }

                    }.show()
                }

                }

            }



        return binding.root
    }
    companion object{
        var Bundle.longArg: Long? by LongArg
    }
}