package com.application.data.model

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
    private val starsCount = 3

    @Ignore
    val period: String = convertToPeriod()

    private fun convertToPeriod(): String {
        return "$countFromDoneDates из $periodicity"
    }

    val stars: String
        get() = "${"★".repeat(priority + 1)}${"☆".repeat(starsCount - priority - 1)}"

    private val countFromDoneDates: Int
        get() = doneDates.size

}

class LongListTypeConverter {
    @TypeConverter
    fun fromString(value: String): List<Int> {
        if (value.isEmpty()) {
            return mutableListOf<Int>()
        }
        return value.split(",").map { it.toInt() }
    }

    @TypeConverter
    fun toString(list: List<Int>): String {
        return list.joinToString(",")
    }
}
