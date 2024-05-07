package com.application.hw2.model

import android.graphics.Color
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.io.Serializable
import java.util.UUID

@Entity(tableName = "habit_table")
data class HabitModel(
    @PrimaryKey var id: String = UUID.randomUUID().toString(),
    val name: String,
    val description: String,
    val priority: Int,
    val type: Int,
    var count: Int = 0,
    val periodicity: Int,
    val color: Int = Color.WHITE,
    val date: Int,
    var doneDates: MutableList<Int>
) : Serializable {
    @Ignore
    private val starsCount = 5

    @Ignore
    val period: String = convertToPeriod()

    private fun convertToPeriod(): String {
        return "${getCountFromDoneDates()} из $periodicity"
    }

    fun getStars(): String {
        return "${"★".repeat(priority)}${"☆".repeat(starsCount - priority)}"
    }

    fun getCountFromDoneDates(): Int {
        return doneDates.size
    }
}

class LongListTypeConverter {
    @TypeConverter
    fun fromString(value: String): List<Int> {
        if (value.isEmpty()) {
            return emptyList()
        }
        return value.split(",").map { it.toInt() }
    }

    @TypeConverter
    fun toString(list: List<Int>): String {
        return list.joinToString(",")
    }
}
