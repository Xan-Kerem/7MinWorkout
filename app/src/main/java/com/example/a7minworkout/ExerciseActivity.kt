package com.example.a7minworkout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.a7minworkout.databinding.ActivityExcerciseBinding

class ExerciseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExcerciseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityExcerciseBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setSupportActionBar(binding.toolbarExerciseActivity)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.toolbarExerciseActivity.setNavigationOnClickListener {
            onBackPressed()
        }

    }
}