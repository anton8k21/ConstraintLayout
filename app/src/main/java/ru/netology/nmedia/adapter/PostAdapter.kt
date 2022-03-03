package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.DisplayingNumbers
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.databinding.CardPostBinding
import java.util.*

interface ClickListener{
    fun onLike(post: Post){}
    fun onRepost(post: Post){}
    fun onRemove(post: Post){}
    fun onEdit(post: Post){}
}

class PostAdapter(
    private val clickListener: ClickListener
): ListAdapter<Post, PostViewHolder>(PostDiffItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder =
        PostViewHolder(
            binding = CardPostBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false),
                    clickListener = clickListener,
       )



    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}

class PostViewHolder(
    private val clickListener: ClickListener,
    private val binding: CardPostBinding,
): RecyclerView.ViewHolder(binding.root){
    private val displayingNumbers = DisplayingNumbers()

    fun bind(post: Post){
        with(binding) {
            author.text = post.author
            content.text = post.content
            published.text = post.published
            repost.text = post.repostSum.toString()
            likes.isChecked = post.likedByMe
            likes.text = post.likesCount.toString()
//            likes.setImageResource(
 //               if (post.likedByMe)  R.drawable.ic_baseline_favorite_24 else R.drawable.ic_baseline_favorite_border_24
 //           )
            likes.setOnClickListener {
                clickListener.onLike(post)
            }
            repost.setOnClickListener {
                clickListener.onRepost(post)
            }
            menu.setOnClickListener {
                PopupMenu(binding.root.context,binding.menu).apply {
                    inflate(R.menu.post_menu)
                    setOnMenuItemClickListener {
                        when(it.itemId){
                            R.id.remove -> {
                                clickListener.onRemove(post)
                                true
                            }
                            R.id.edit -> {
                                clickListener.onEdit(post)
                                true
                            }
                            else -> false
                        }
                    }

                }.show()
            }
        }
        }

    }

class PostDiffItemCallback: DiffUtil.ItemCallback<Post>(){
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean =
        oldItem == newItem

}
