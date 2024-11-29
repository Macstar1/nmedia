package ru.netology.nmedia

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.VievModel.PostViewModel
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.util.AndroidUtils
import ru.netology.nmedia.util.focusAndShowKeyboard

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()

        val adapter = PostAdapter(object: OnInteractionListener{
            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onEdit(post: Post) {
                viewModel.edit(post)
            }

            override fun onShare(post: Post) {
                viewModel.increaseShare(post.id)
            }

        })

        binding.list.adapter = adapter
        binding.save.setOnClickListener {
            val text = binding.content.text.toString()

            if (text.isBlank()) {
                Toast.makeText(this, R.string.error_message, Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            viewModel.saveContent(text)
            binding.content.setText("")
            binding.content.clearFocus()
            AndroidUtils.hideKeyboard(it)
        }

        viewModel.post.observe(this) { posts ->
            posts.map { post ->
                val new = adapter.currentList.size < posts.size
                adapter.submitList(posts) {
                    if (new) {
                        binding.list.smoothScrollToPosition(0)
                    }
                }
            }
        }
        viewModel.edited.observe(this){
            if (it.id != 0L){
                binding.content.setText(it.content)
                binding.content.focusAndShowKeyboard()
//                binding.content.requestFocus()
            }

        }
    }
}