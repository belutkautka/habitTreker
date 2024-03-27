package com.application.hw2.db

import com.application.hw2.model.HabitModel

// Это симуляция бд
object HabitsList {
    private val habits = ArrayList<HabitModel>();

    fun init(){
        val habit1 = HabitModel(
            "Первая привычка", "Описание какое-то",
            0, "GOOD", 1, "неделю"
        )
        val habit2 = HabitModel(
            "Вторая привычка", "Описание какое-тооооооо",
            5, "BAD", 2, "месяц"
        )
        val habit3 = HabitModel(
            "Третья привычка", "К черту описание",
            3, "GOOD", 10, "год"
        )
        habits.add(habit1)
        habits.add(habit2)
        habits.add(habit3)
    }

    fun selectAllHabits(): List<HabitModel> {
        return habits.toList()
    }

    fun selectHabitsByType(type: String): List<HabitModel>{
        return habits.filter { habit -> habit.type == type}
    }

    fun insertIntoPosition(habit: HabitModel, position: Int) {
        habits[position] = habit
    }

    fun insertToEnd(habit: HabitModel) {
        habits.add(habit)
    }
}