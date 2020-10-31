package com.example.a7minworkout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.a7minworkout.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarHistoryActivity)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "HISTORY"
        }
        binding.toolbarHistoryActivity.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}