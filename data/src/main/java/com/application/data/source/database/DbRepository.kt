package com.application.data.source.database

import com.application.domain.useCases.model.HabitModel


open class DbRepository(private val dao: HabitsDao) {
    suspend fun insertHabit(habit: HabitModel) = dao.insertHabit(habit)
    suspend fun getAllHabits(): List<HabitModel> = dao.getAllHabits()
    suspend fun deleteHabit(habit: HabitModel) = dao.deleteHabit(habit)

    suspend fun getHabitsSortedByPriority(desc: Boolean): List<HabitModel> = dao.getHabitsSortedByPriority(desc)
    suspend fun getHabitsFilteredByName(name: String): List<HabitModel> = dao.getHabitsFilteredByName(name)
}