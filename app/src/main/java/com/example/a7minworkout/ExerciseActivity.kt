package com.example.a7minworkout

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.a7minworkout.databinding.ActivityExcerciseBinding

class ExerciseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExcerciseBinding
    private lateinit var restTimer: CountDownTimer
    private var restProgress = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityExcerciseBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setSupportActionBar(binding.toolbarExerciseActivity)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.toolbarExerciseActivity.setNavigationOnClickListener {
            onBackPressed()
        }

        setRestView()

    }

    private fun setRestProgressBar() {

        binding.progressBar.progress = restProgress

        restTimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding.progressBar.progress = 10 - restProgress

                binding.tvTimer.text = (10 - restProgress).toString()

            }

            override fun onFinish() {
                Toast.makeText(
                    this@ExerciseActivity,
                    "Here now we will start the exercise",
                    Toast.LENGTH_SHORT
                ).show()

            }

        }.start()
    }

    private fun setRestView() {
        if (::restTimer.isInitialized) {
            restTimer.cancel()
            restProgress = 0
        }
        setRestProgressBar()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::restTimer.isInitialized) {
            restTimer.cancel()
        }
    }
}