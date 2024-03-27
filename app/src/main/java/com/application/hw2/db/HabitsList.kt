package com.application.hw2.db

import com.application.hw2.model.HabitModel

// Это симуляция бд
object HabitsList {
    private val habits = ArrayList<HabitModel>();
    var changed = false

    fun selectAllHabits(): List<HabitModel> {
        changed = false
        return habits.toList()
    }

    fun selectHabitsByType(type: String): List<HabitModel>{
        changed = false
        return habits.filter { habit -> habit.type == type}
    }

    fun insertIntoPosition(habit: HabitModel, position: Int) {
        habits[position] = habit
        changed = true
    }

    fun insertToEnd(habit: HabitModel) {
        habits.add(habit)
        changed = true
    }
}