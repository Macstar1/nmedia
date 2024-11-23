package ru.netology.nmedia

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.VievModel.PostViewModel
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.databinding.PostCardBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()

        val adapter = PostAdapter(
            {viewModel.likeById(it.id)},
            {viewModel.increaseShare(it.id)},
        )


        binding.list.adapter = adapter


        viewModel.post.observe(this) { posts ->
            posts.map { post ->
                adapter.submitList(posts)
            }
        }
    }
}