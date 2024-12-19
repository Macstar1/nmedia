package ru.netology.nmedia

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.VievModel.PostViewModel
import ru.netology.nmedia.databinding.FragmentNewPostBinding
import ru.netology.nmedia.util.StringArg

class NewPostFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentNewPostBinding.inflate(inflater, container, false)

        val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)

        arguments?.textArg?.let { binding.edit.setText(it) }

        val intent = Intent()
        binding.edit.setText(intent.getStringExtra("text_old"))

        binding.ok.setOnClickListener{
            val text = binding.edit.text.toString()
            if (text.isNotBlank()) {
                viewModel.saveContent(text)
            }
            findNavController().navigateUp()
//
//            if (text.isBlank()) {
//                Snackbar.make(binding.root, "Text is empty", Snackbar.LENGTH_INDEFINITE)
//                    .setAction(android.R.string.ok){
//                        activity?.finish()
//                    }.show()
//                activity?.setResult(RESULT_CANCELED)
//            } else{
//                activity?.setResult(RESULT_OK, Intent().apply { putExtra("text", text) })
//            }
//            activity?.finish()
        }
        return binding.root
    }

    companion object{
        var Bundle.textArg by StringArg
    }

}
//
//object NewPostContract: ActivityResultContract<String?, String?>() {
//    override fun createIntent(context: Context, input: String?): Intent {
//        return Intent(context, NewPostFragment::class.java).putExtra("text_old", input)
//    }
//
//    override fun parseResult(resultCode: Int, intent: Intent?) = intent?.getStringExtra("text")
//}