package com.example.a7minworkout

data class ExerciseModel(
    private val id: Int, private val name: String, private val image: Int,
    private val isCompleted: Boolean, private val isSelected: Boolean
)