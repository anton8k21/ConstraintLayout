package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.NewPostFragment.Companion.textArg
import ru.netology.nmedia.activity.PostContent.Companion.longArg
import ru.netology.nmedia.adapter.ClickListener
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.databinding.FeedFragmentBinding
import ru.netology.nmedia.databinding.PostFragmentBinding
import ru.netology.nmedia.viewmodel.PostViewModel

class FeedFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FeedFragmentBinding.inflate(layoutInflater)
        val viewModel: PostViewModel by viewModels(::requireParentFragment)


        val adapter = PostAdapter(
            object : ClickListener {
                override fun onLike(post: Post) {
                    viewModel.likeById(post.id)
                }

                override fun openCardPost(post: Post) {
                    findNavController().navigate(R.id.action_feedFragment_to_postContent,
                    Bundle().apply
                     {longArg = post.id})
                }

                override fun onRepost(post: Post) {
                    val intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, post.content)
                    }
                    val repostIntent = Intent.createChooser(intent, "Поделиться")
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
                    val videoIntent = Intent.createChooser(intent, "")
                    startActivity(videoIntent)
                }

            }
        )

        binding.list.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            adapter.submitList(posts)
        }

        binding.add.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_newPostFragment)



        }

        viewModel.edited.observe(viewLifecycleOwner) {
            if (it.id == 0L) {
                return@observe
            }

            findNavController().navigate(R.id.action_feedFragment_to_newPostFragment,
            Bundle().apply { textArg = it.content})

        }
        return binding.root
    }

}



