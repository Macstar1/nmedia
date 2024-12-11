package ru.netology.nmedia

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia.databinding.ActivityNewPostBinding

class NewPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityNewPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.edit.setText(intent.getStringExtra("text_old"))

        binding.ok.setOnClickListener{
            val text = binding.edit.text.toString()
            if (text.isBlank()) {
                Snackbar.make(binding.root, "Text is empty", Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok){
                        finish()
                    }.show()
                setResult(RESULT_CANCELED)
            } else{
                setResult(RESULT_OK, Intent().apply { putExtra("text", text) })
            }
            finish()
        }
    }
}

object NewPostContract: ActivityResultContract<String?, String?>() {
    override fun createIntent(context: Context, input: String?): Intent {
        return Intent(context, NewPostActivity::class.java).putExtra("text_old", input)
    }

    override fun parseResult(resultCode: Int, intent: Intent?) = intent?.getStringExtra("text")
}