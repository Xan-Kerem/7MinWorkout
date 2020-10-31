package com.example.a7minworkout

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.a7minworkout.databinding.ActivityBMIBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBMIBinding

    private val MERTRIC_UNITS_VIEW = "MERTRIC_UNITS_VIEW"
    private val US_UNITS_VIEW = "US_UNITS_VIEW"
    private var currentVisibleView = MERTRIC_UNITS_VIEW

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBMIBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarBmiActivity)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "BMI"
        }
        binding.toolbarBmiActivity.setNavigationOnClickListener {
            onBackPressed()
        }


        binding.btnCalculateUnits.setOnClickListener {
            if (currentVisibleView == MERTRIC_UNITS_VIEW) {
                if (validMetricUnits()) {
                    val heightValue = binding.etMetricUnitHeight.text.toString().toFloat() / 100
                    val weightValue = binding.etMetricUnitWeight.text.toString().toFloat()

                    val bmi = weightValue / (heightValue * heightValue)
                    displayBMIResult(bmi)

                } else {
                    Toast.makeText(this, "Please enter valid values", Toast.LENGTH_SHORT).show()
                }
            } else if (currentVisibleView == US_UNITS_VIEW) {
                if (validUsUnits()) {
                    val usUnitHeightValueFeet = binding.etUsUnitHeightFeet.text.toString()
                    val usUnitHeightValueInch = binding.etUsUnitHeightInch.text.toString()
                    val usUnitWeightValue = binding.etUsUnitWeight.text.toString().toFloat()

                    val heightValue =
                        usUnitHeightValueInch.toFloat() + usUnitHeightValueFeet.toFloat() * 12

                    val bmi = 703 * (usUnitWeightValue / (heightValue * heightValue))
                    displayBMIResult(bmi)

                } else {
                    Toast.makeText(this, "Please enter valid values", Toast.LENGTH_SHORT).show()
                }
            }
        }

        makeVisibleMetricUnitsView()
        binding.rgUnits.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                binding.rbMetricUnits.id -> {
                    makeVisibleMetricUnitsView()
                }
                else -> {
                    makeVisibleUsUnitsView()
                }
            }
        }
    }

    private fun validUsUnits(): Boolean {

        var isValid = true

        when {
            binding.etUsUnitHeightFeet.text.isNullOrBlank() -> {
                isValid = false
            }
            binding.etUsUnitHeightInch.text.isNullOrBlank() -> {
                isValid = false
            }
            binding.etUsUnitWeight.text.isNullOrBlank() -> {
                isValid = false
            }
        }

        return isValid
    }

    private fun validMetricUnits(): Boolean {
        var isValid = true

        if (binding.etMetricUnitHeight.text.isNullOrBlank()) {
            isValid = false
        } else if (binding.etMetricUnitWeight.text.isNullOrBlank()) {
            isValid = false
        }
        return isValid
    }

    private fun displayBMIResult(bmi: Float) {

        val bmiLabel: String
        val bmiDescription: String

        if (bmi.compareTo(15f) <= 0) {
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take care of your better! Eat more!"
        } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0
        ) {
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops! You really need to take care of your better! Eat more!"
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0
        ) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take care of your better! Eat more!"
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0
        ) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        } else if (bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0
        ) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0
        ) {
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0
        ) {
            bmiLabel = "Obese Class || (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        } else {
            bmiLabel = "Obese Class ||| (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }

        binding.llDiplayBMIResult.visibility = View.VISIBLE

        // This is used to round of the result value to 2 decimal values after "."
        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        binding.tvBMIValue.text = bmiValue // Value is set to TextView
        binding.tvBMIType.text = bmiLabel // Label is set to TextView
        binding.tvBMIDescription.text = bmiDescription // Description is set to TextView
    }

    private fun makeVisibleMetricUnitsView() {
        currentVisibleView = MERTRIC_UNITS_VIEW
        binding.apply {

            llUsUnitsView.visibility = View.GONE
            llMetricUnitsView.visibility = View.VISIBLE

            llDiplayBMIResult.visibility = View.GONE

            etMetricUnitHeight.text?.clear()
            etMetricUnitWeight.text?.clear()

        }


    }

    private fun makeVisibleUsUnitsView() {
        currentVisibleView = US_UNITS_VIEW
        binding.apply {

            llMetricUnitsView.visibility = View.GONE
            llUsUnitsView.visibility = View.VISIBLE


            llDiplayBMIResult.visibility = View.GONE

            etUsUnitHeightFeet.text?.clear()
            etUsUnitHeightInch.text?.clear()
            etUsUnitWeight.text?.clear()
        }


    }
}