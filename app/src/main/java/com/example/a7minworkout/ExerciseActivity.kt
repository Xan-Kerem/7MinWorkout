package com.example.a7minworkout

import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.a7minworkout.databinding.ActivityExcerciseBinding
import java.util.*

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var binding: ActivityExcerciseBinding
    private lateinit var restTimer: CountDownTimer
    private var restProgress = 0
    private lateinit var exerciseTimer: CountDownTimer
    private var exerciseProgress = 0

    private lateinit var exerciseList: ArrayList<ExerciseModel>
    private var currentExercisePosition = -1

    private lateinit var textToSpeech: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityExcerciseBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setSupportActionBar(binding.toolbarExerciseActivity)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.toolbarExerciseActivity.setNavigationOnClickListener {
            onBackPressed()
        }

        textToSpeech = TextToSpeech(this, this)

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
        binding.tvUpcomingExerciseName.text = exerciseList[currentExercisePosition + 1].name
        setRestProgressBar()
    }

    private fun setExerciseView() {
        if (::exerciseTimer.isInitialized) {
            exerciseTimer.cancel()
            exerciseProgress = 0
        }

        speakOut(exerciseList[currentExercisePosition].name)

        setExerciseProgressBar()
    }

    private fun setExerciseProgressBar() {

        binding.progressBarExercise.progress = exerciseProgress
        binding.ivImage.setImageResource(exerciseList[currentExercisePosition].image)
        binding.tvExerciseName.text = exerciseList[currentExercisePosition].name

        exerciseTimer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                binding.progressBarExercise.progress = 30 - exerciseProgress

                binding.tvExerciseTimer.text = (30 - exerciseProgress).toString()

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
        if (::textToSpeech.isInitialized) {
            textToSpeech.stop()
            textToSpeech.shutdown()
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = textToSpeech.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(
                    this,
                    "Text to speech not working on this device",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Toast.makeText(this, "Initialization Failed!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun speakOut(text: String) {
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }
}