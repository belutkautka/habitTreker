package com.application.hw2.db

import androidx.lifecycle.LiveData
import com.application.hw2.model.HabitModel

class HabitRepository(private val habitDao: HabitsDao) {
    var allHabits: LiveData<List<HabitModel>> = habitDao.selectAllHabits()

    suspend fun insert(habit: HabitModel) {
        habitDao.insertHabit(habit)
        allHabits = habitDao.selectAllHabits()
    }

    suspend fun update(habit: HabitModel) {
        habitDao.updateHabit(habit)
        allHabits = habitDao.selectAllHabits()
    }
}
