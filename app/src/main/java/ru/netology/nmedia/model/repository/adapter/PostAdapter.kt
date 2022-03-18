package ru.netology.nmedia.model.repository.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.model.repository.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.model.repository.DisplayingNumbers

import ru.netology.nmedia.databinding.CardPostBinding

interface ClickListener{
    fun onLike(post: Post){}
    fun onRepost(post: Post){}
    fun onRemove(post: Post){}
    fun onEdit(post: Post){}
    fun onPlayVideo(url: String, id: Long){}
    fun openCardPost(post: Post){}
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
//            likes.setImageResource(
 //               if (post.likedByMe)  R.drawable.ic_baseline_favorite_24 else R.drawable.ic_baseline_favorite_border_24
 //           )
            likes.setOnClickListener {
                clickListener.onLike(post)
            }
            repost.setOnClickListener {
                clickListener.onRepost(post)
            }
            content.setOnClickListener{
                clickListener.openCardPost(post)
            }
            addVideo.setOnClickListener {
                clickListener.onPlayVideo(urlVideo.text.toString(), post.id)
                urlVideo.visibility = View.GONE
                postVideo.visibility = View.VISIBLE
                addVideo.visibility = View.GONE
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