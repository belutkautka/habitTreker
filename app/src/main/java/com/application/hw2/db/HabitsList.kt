package com.application.hw2.db

import com.application.hw2.enums.HabitType
import com.application.hw2.model.HabitModel

// Это симуляция бд
object HabitsList {
    private val habits = ArrayList<HabitModel>();

    fun selectAllHabits(): List<HabitModel> {
        return habits.toList()
    }

    fun selectHabitsByType(type: HabitType): List<HabitModel>{
        return habits.filter { habit -> habit.type == type}
    }

    fun insertIntoPosition(habit: HabitModel, position: Int) {
        habits[position] = habit
    }

    fun insertToEnd(habit: HabitModel) {
        habits.add(habit)
    }
}