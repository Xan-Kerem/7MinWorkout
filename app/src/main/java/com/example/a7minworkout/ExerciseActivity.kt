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

        binding.llRestView.visibility = View.VISIBLE
        binding.llExerciseView.visibility = View.GONE

        restTimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding.progressBar.progress = 10 - restProgress

                binding.tvTimer.text = (10 - restProgress).toString()

            }

            override fun onFinish() {
                binding.llRestView.visibility = View.GONE
                binding.llExerciseView.visibility = View.VISIBLE
                currentExercisePosition++
                setExerciseView()
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
        binding.ivImage.setImageResource(exerciseList[currentExercisePosition].image)
        binding.tvExerciseName.text = exerciseList[currentExercisePosition].name

        exerciseTimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                binding.progressBarExercise.progress = 10 - exerciseProgress

                binding.tvExerciseTimer.text = (10 - exerciseProgress).toString()

            }

            override fun onFinish() {
                if (currentExercisePosition < exerciseList.size - 1) {
                    setRestView()
                } else {
                    Toast.makeText(
                        this@ExerciseActivity,
                        "Congratulations! U'v completed the 7 min workout!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
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