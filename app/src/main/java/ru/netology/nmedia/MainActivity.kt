package ru.netology.nmedia

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.VievModel.PostViewModel
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()

        val newPostLauncher = registerForActivityResult(NewPostContract) { result ->
            if (result == null) {
                viewModel.clearEdit()
                return@registerForActivityResult
            }
            viewModel.saveContent(result)
        }

        val adapter = PostAdapter(object : OnInteractionListener {
            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onEdit(post: Post) {
                viewModel.edit(post)
                newPostLauncher.launch(post.content)
            }

            override fun onShare(post: Post) {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, post.content)
                }
                val shareIntent = Intent.createChooser(intent, post.content)
                startActivity(shareIntent)
            }

            override fun onVideo(post: Post) {
                val youtubeLink = post.video
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(youtubeLink)
                    // Ограничение выбора только определённого приложения
                    setPackage("com.google.android.youtube") // applicationId можно посмотреть в Device Explorer: data/data
                }

                try {
                    startActivity(intent) // Попробовать открыть с выбранным приложением
                } catch (e: ActivityNotFoundException) {
                    // Если приложение недоступно, открыть там где это возможно
                    val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
                    startActivity(webIntent)
                }
            }
        })

        binding.save.setOnClickListener {
            newPostLauncher.launch(null)
        }

        binding.list.adapter = adapter

        viewModel.post.observe(this) { posts ->
            posts.map {
                val new = adapter.currentList.size < posts.size
                adapter.submitList(posts) {
                    if (new) {
                        binding.list.smoothScrollToPosition(0)
                    }
                }
            }
        }
    }
}