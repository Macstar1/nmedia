package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.PostCardBinding
import ru.netology.nmedia.dto.Post

interface OnInteractionListener {
    fun onLike(post: Post)
    fun onRemove(post: Post)
    fun onEdit(post: Post)
    fun onShare(post: Post)
}

class PostAdapter(
    private val onInteractionListener: OnInteractionListener,
) : ListAdapter<Post, PostViewHolder>(PostDiffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PostViewHolder {
        return PostViewHolder(
            PostCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onInteractionListener
        )
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class PostViewHolder(
    private val binding: PostCardBinding,
    private val onInteractionListener: OnInteractionListener,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) = with(binding) {
        mainText.text = post.content
        messageText.text = post.author
        messageDate.text = post.published
        likeCounter.text = toShort(post.likeCounter)
        shareCounter.text = toShort(post.shared)
        like.setImageResource(
            if (post.likedByMe) {
                R.drawable.red_favorite_24
            } else {
                R.drawable.grey_favorite_border_24
            }
        )
        like.setOnClickListener {
            onInteractionListener.onLike(post)
        }
        share.setOnClickListener {
            onInteractionListener.onShare(post)
        }
        menu.setOnClickListener{
            PopupMenu(it.context, it).apply {
                inflate(R.menu.menu_options)
                setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.remove -> {
                            onInteractionListener.onRemove(post)
                            true
                        }
                        R.id.edit -> {
                            onInteractionListener.onEdit(post)
                            true
                        }
                        else -> false
                    }
                }
            }.show()
        }
    }

    private fun toShort(i: Int): String {
        return when (i) {
            in 0..999 -> "" + i
            in 1_000..9_999 -> "" + (i / 100).toDouble() / 10 + "K"
            in 10_000..999_999 -> "" + i / 1000 + "K"

            else -> {
                "" + (i / 100_000).toDouble() / 10 + "M"
            }
        }
    }
}

object PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }
}