package com.example.theweather.fragments

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.theweather.databinding.FragmentShareBinding

class ReceiveMessageActivity : AppCompatActivity() {
    companion object{
        const val EXTRA_MESSAGE: String = "message"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = FragmentShareBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent: Intent = intent
        val messageText: String = intent.getStringExtra(EXTRA_MESSAGE).toString()
        binding.gotMessage.text = messageText

    }
}