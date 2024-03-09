package com.application.hw2.model

data class HabitModel(
    var name: String,
    var description: String,
    var priority: String,
    var type: String,
    var count: Int,
    var periodicity: String
) {
    var period: String = ""
        get() = convertToPeriod()
    var exceptionsNumbers = intArrayOf(2,3,4)

    private fun convertToPeriod(): String{
        if (count in exceptionsNumbers){
            return "$count раза в $periodicity"
        }
        return "$count paз в $periodicity"
    }
}