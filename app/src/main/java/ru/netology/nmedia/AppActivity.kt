package ru.netology.nmedia

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia.NewPostFragment.Companion.textArg
import ru.netology.nmedia.databinding.ActivityAppBinding

class AppActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAppBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent?.let {
            val text = it.getStringExtra(Intent.EXTRA_TEXT)

            if (it.action == Intent.ACTION_SEND) {
                if (text.isNullOrBlank()) {
                    Snackbar.make(binding.root, "Text is empty", Snackbar.LENGTH_INDEFINITE)
                        .setAction(android.R.string.ok) {
                            finish()
                        }
                        .show()
                }
                findNavController(R.id.main).navigate(
                    R.id.action_feedFragment_to_newPostFragment,
                    Bundle().apply { textArg = text }
                )
            }


        }
    }
}