package com.example.a7minworkout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.a7minworkout.databinding.ActivityBMIBinding

class BMIActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBMIBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBMIBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarBmiActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarBmiActivity.setNavigationOnClickListener {
            onBackPressed()
        }


    }
}