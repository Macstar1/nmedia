package ru.netology.nmedia

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий.",
            published = "Сегодня",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, дизайну, аналитике и управлению. Мы растём сами и помогаем студентам: от новичков до уверенных профессианалов. Но самое важноеостаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиься точнее, бежать быстрее. Наша миссия - помочь встать на путь роста и начать цепочку перемен.",
            likedByMe = false,
            likeCounter = 1100,
            shared = 1000999,
        )
        with(binding) {
            mainText.text = post.content
            messageText.text = post.author
            messageDate.text = post.published
            likeCounter.text = toShort(post.likeCounter)
            shareCounter.text = toShort(post.shared)
        }

        updateLike(binding, post)
        increaseShare(binding, post)
        binding.root.setOnClickListener { println("root") }
        binding.like.setOnClickListener{ println("like") }
        binding.icNetology48dp.setOnClickListener{ println("avatar") }
    }

    private fun increaseShare(
        binding: ActivityMainBinding,
        post: Post
    ) {
        binding.share.setOnClickListener {
            post.shared = post.shared.plus(1)
            binding.shareCounter.text = toShort(post.shared)
        }
    }

    private fun updateLike(
        binding: ActivityMainBinding,
        post: Post
    ) {
        binding.like.setOnClickListener {
            post.likedByMe = !post.likedByMe
            if (post.likedByMe) {
                post.likeCounter = post.likeCounter.plus(1)
                binding.likeCounter.text = toShort(post.likeCounter)
            } else {
                post.likeCounter = post.likeCounter.minus(1)
                binding.likeCounter.text = toShort(post.likeCounter)
            }

            binding.like.setImageResource(
                if (post.likedByMe) {
                    R.drawable.red_favorite_24
                } else {
                    R.drawable.grey_favorite_border_24
                }
            )
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