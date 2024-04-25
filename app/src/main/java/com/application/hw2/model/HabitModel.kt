package com.application.hw2.model

import android.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.application.hw2.enums.HabitType
import java.io.Serializable

@Entity
data class HabitModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    var name: String,
    var description: String,
    var priority: Int,
    var type: Int,
    var count: Int,
    var periodicity: String,
    var color:Int = Color.WHITE
) : Serializable {
    var starsCount = 5
    var period: String = ""
        get() = convertToPeriod()


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

//    class ListConverter {
//        @TypeConverter
//        fun fromHabits(habits: MutableList<Long>): String {
//            return habits.joinToString()
//        }
//
//        @TypeConverter
//        fun toHabits(data: String): MutableList<Long> {
//            return if (data != "") {
//                data.split(", ").map { it.toLong() }.toMutableList()
//            } else {
//                mutableListOf()
//            }
//        }
//    }

}