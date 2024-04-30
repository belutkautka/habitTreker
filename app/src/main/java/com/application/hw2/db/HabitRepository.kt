package com.application.hw2.db

import androidx.lifecycle.LiveData
import com.application.hw2.model.HabitModel

class HabitRepository(private val habitDao: HabitsDao) {
    val allHabits: LiveData<List<HabitModel>> = habitDao.getAllHabits()

    suspend fun insert(habit: HabitModel) {
        habitDao.insertHabit(habit)
    }

    suspend fun getHabitsSortedByPriority(desc: Boolean): List<HabitModel> =
        habitDao.getHabitsSortedByPriority(desc)

    suspend fun getHabitsFilteredByName(name: String): List<HabitModel> =
        habitDao.getHabitsFilteredByName(name)
}
