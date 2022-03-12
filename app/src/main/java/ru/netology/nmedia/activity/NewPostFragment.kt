package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.FragmentNewPostBinding
import ru.netology.nmedia.util.StringArg
import ru.netology.nmedia.viewmodel.PostViewModel

class NewPostFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNewPostBinding.inflate(layoutInflater)
        val viewModel: PostViewModel by viewModels(::requireParentFragment)

        arguments?.textArg.let {
            binding.editContent.setText(it)
        }

        binding.editContent.requestFocus()
        binding.save.setOnClickListener{
            val text = binding.editContent.text.toString()
            if (text.isNotBlank()){
                viewModel.changeContent(text)
                viewModel.save()
            }
            findNavController().navigateUp()
            }

        binding.deleteTextContent.setOnClickListener{
            binding.editContent.setText("")
            findNavController().navigateUp()

        }
        return binding.root
    }
    companion object{
        var Bundle.textArg: String? by StringArg
    }
}