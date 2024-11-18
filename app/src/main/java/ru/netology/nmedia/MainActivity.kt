package ru.netology.nmedia

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.VievModel.PostViewModel
import ru.netology.nmedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()

        viewModel.post.observe(this) {post ->
            with(binding) {
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
            }

        }
        binding.like.setOnClickListener {
            viewModel.like()
        }

        binding.share.setOnClickListener {
            viewModel.increaseShare()
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