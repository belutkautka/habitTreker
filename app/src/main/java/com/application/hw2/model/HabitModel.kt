package com.application.hw2.model

import java.io.Serializable

data class HabitModel(
    var name: String,
    var description: String,
    var priority: Int,
    var type: String,
    var count: Int,
    var periodicity: String
) : Serializable {
    var MAX_STARS_COUNT = 5
    var period: String = ""
        get() = convertToPeriod()
    var exceptionsNumbers = intArrayOf(2, 3, 4)
    var position: Int = 0;

    private fun convertToPeriod(): String {
        if (count in exceptionsNumbers) {
            return "$count раза в $periodicity"
        }
        return "$count paз в $periodicity"
    }

    public fun getStars(): String {
        return "${"★".repeat(priority)}${"☆".repeat(MAX_STARS_COUNT - priority)}"
    }
}