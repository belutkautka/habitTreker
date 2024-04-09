package com.application.hw2.model

import android.graphics.Color
import com.application.hw2.enums.HabitType
import java.io.Serializable

data class HabitModel(
    val name: String,
    val description: String,
    val priority: Int,
    val type: HabitType,
    val count: Int,
    val periodicity: String,
    var color:Int = Color.WHITE
) : Serializable {
    private val starsCount = 5
    val period: String
        get() = convertToPeriod()
    private var exceptionsNumbers = intArrayOf(2, 3, 4)

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