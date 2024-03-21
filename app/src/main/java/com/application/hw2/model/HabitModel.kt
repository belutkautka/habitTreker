package com.application.hw2.model

import android.graphics.Color
import java.io.Serializable

data class HabitModel(
    val name: String,
    val description: String,
    val priority: Int,
    val type: String,
    val count: Int,
    val periodicity: String
) : Serializable {
    private val starsCount = 5
    val period: String
        get() = convertToPeriod()
    var exceptionsNumbers = intArrayOf(2, 3, 4)
    var color: Int = Color.WHITE;

    private fun convertToPeriod(): String {
        if (count in exceptionsNumbers) {
            return "$count раза в $periodicity"
        }
        return "$count paз в $periodicity"
    }

    fun getStars(): String {
        return "${"★".repeat(priority)}${"☆".repeat(starsCount - priority)}"
    }
}