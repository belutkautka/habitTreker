package com.application.hw2.db

import com.application.hw2.model.HabitModel

// Это симуляция бд
object HabitsList {
    val habits: MutableList<HabitModel> = mutableListOf()

    fun updateHabit(oldHabit: HabitModel, newHabit: HabitModel) {
        val index = habits.indexOf(oldHabit)
        habits.removeAt(index)
        habits.add(newHabit)
    }

    fun insertToEnd(habit: HabitModel) {
        habits.add(habit)
    }
}