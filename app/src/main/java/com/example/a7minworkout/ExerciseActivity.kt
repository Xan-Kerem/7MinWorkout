package com.example.a7minworkout

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.a7minworkout.databinding.ActivityExcerciseBinding

class ExerciseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExcerciseBinding
    private lateinit var restTimer: CountDownTimer
    private var restProgress = 0
    private lateinit var exerciseTimer: CountDownTimer
    private var exerciseProgress = 0

    private lateinit var exerciseList: ArrayList<ExerciseModel>
    private var currentExercisePosition = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityExcerciseBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setSupportActionBar(binding.toolbarExerciseActivity)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.toolbarExerciseActivity.setNavigationOnClickListener {
            onBackPressed()
        }

        exerciseList = ExerciseConstants.defaultExerciseList()

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
                binding.llRestView.visibility = View.GONE
                binding.llExerciseView.visibility = View.VISIBLE
                setExerciseView()
                currentExercisePosition++
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

    private fun setExerciseView() {
        if (::exerciseTimer.isInitialized) {
            exerciseTimer.cancel()
            exerciseProgress = 0
        }
        setExerciseProgressBar()
    }

    private fun setExerciseProgressBar() {

        binding.progressBarExercise.progress = exerciseProgress

        exerciseTimer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                binding.progressBarExercise.progress = 30 - exerciseProgress

                binding.tvExerciseTimer.text = (30 - exerciseProgress).toString()

            }

            override fun onFinish() {
                Toast.makeText(
                    this@ExerciseActivity,
                    "Here u are!",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::restTimer.isInitialized) {
            restTimer.cancel()
        }
        if (::exerciseTimer.isInitialized) {
            exerciseTimer.cancel()
        }
    }
}