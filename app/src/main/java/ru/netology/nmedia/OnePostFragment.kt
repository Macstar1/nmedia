package ru.netology.nmedia

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.NewPostFragment.Companion.textArg
import ru.netology.nmedia.VievModel.PostViewModel
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostViewHolder
import ru.netology.nmedia.databinding.FragmentOnePostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.util.LongArg

class OnePostFragment : Fragment() {
    val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentOnePostBinding.inflate(inflater, container, false)
        val id = arguments?.idArg ?: -1
        val holder = PostViewHolder(binding.post, object : OnInteractionListener {
            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
                findNavController().navigateUp()
            }

            override fun onEdit(post: Post) {
                viewModel.edit(post)

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

            override fun onView(post: Post) {
                onEdit(post)
            }
        })

        viewModel.edited.observe(viewLifecycleOwner) {
            if (it.id != 0L) {
                findNavController().navigate(
                    R.id.action_onePostFragment_to_newPostFragment,
                    Bundle().apply { textArg = it.content }
                )
            }
        }

        viewModel.post.observe(viewLifecycleOwner) { posts ->
            val post = posts.find { it.id == id }
            post?.let { holder.bind(it) }

        }
        return binding.root
    }

    companion object {
        var Bundle.idArg: Long by LongArg
    }
}