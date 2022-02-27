package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.DisplayingNumbers
import ru.netology.nmedia.databinding.CardPostBinding
typealias LikeClickListener = (Post) -> Unit
typealias RepostClickListener = (Post) -> Unit

class PostAdapter(
    private val likeClickListener: LikeClickListener,
    private val repostClickListener: RepostClickListener,
): ListAdapter<Post, PostViewHolder>(PostDiffItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder =
        PostViewHolder(
            binding = CardPostBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false),
                    likeClickListener = likeClickListener,
            repostClickListener = repostClickListener
       )



    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}

class PostViewHolder(
    private val likeClickListener: LikeClickListener,
    private val repostClickListener: RepostClickListener,
    private val binding: CardPostBinding
): RecyclerView.ViewHolder(binding.root){
    val displayingNumbers = DisplayingNumbers()

    fun bind(post: Post){
        with(binding) {
            author.text = post.author
            content.text = post.content
            published.text = post.published
            repostSum.text = displayingNumbers.displaying(post.repostSum).toString()
            likesSum.text = displayingNumbers.displaying(post.likesCount).toString()
            likes.setImageResource(
                if (post.likedByMe)  R.drawable.ic_baseline_favorite_24 else R.drawable.ic_baseline_favorite_border_24
            )
            likes.setOnClickListener {
                likeClickListener(post)
            }
            repost.setOnClickListener {
                repostClickListener(post)
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
