package com.application.hw2.model

import android.graphics.Color
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity("habit_table")
data class HabitModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val name: String,
    val description: String,
    val priority: Int,
    val type: Int,
    val count: Int,
    val periodicity: String,
    val color: Int = Color.WHITE
) : Serializable {
    @Ignore
    private val starsCount = 5

    @Ignore
    val period: String = convertToPeriod()

    private fun convertToPeriod(): String {
        val exceptionsNumbers = intArrayOf(2, 3, 4)
        if (count in exceptionsNumbers) {
            return "$count раза в $periodicity"
        }
        return "$count paз в $periodicity"
    }

    fun getStars(): String {
        return "${"★".repeat(priority)}${"☆".repeat(starsCount - priority)}"
    }
}