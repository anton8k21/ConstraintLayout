package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.launch
import androidx.activity.viewModels
import ru.netology.nmedia.databinding.ActivityNewPostBinding
import ru.netology.nmedia.viewmodel.PostViewModel

class NewPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityNewPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent?.getStringExtra(Intent.EXTRA_TEXT).let {
            binding.editContent.setText(it)
        }

        binding.editContent.requestFocus()
        binding.save.setOnClickListener{
            val text = binding.editContent.text.toString()
            if (text.isBlank()){
                setResult(Activity.RESULT_CANCELED)
            }else {
                setResult(Activity.RESULT_OK, Intent().apply { putExtra(Intent.EXTRA_TEXT, text) })
            }
            finish()
        }

        binding.deleteTextContent.setOnClickListener{
            binding.editContent.setText("")
            finish()
        }

    }

    class Contract : ActivityResultContract<String,String?>(){
        override fun createIntent(context: Context, input: String): Intent {
            return Intent(context, NewPostActivity :: class.java).apply { putExtra(Intent.EXTRA_TEXT, input)}
        }

        override fun parseResult(resultCode: Int, intent: Intent?): String? {
            return if (resultCode == RESULT_OK) {
                intent?.getStringExtra(Intent.EXTRA_TEXT)
            } else {
                null
            }
        }


    }

}