package com.application.hw2.db

import android.util.Log
import androidx.lifecycle.LiveData
import com.application.hw2.api.ApiService
import com.application.hw2.model.HabitConverter
import com.application.hw2.model.HabitModel
import com.google.gson.Gson

class HabitRepository(private val habitDao: HabitsDao) {
    val allHabits: LiveData<List<HabitModel>> = habitDao.getAllHabits()
    private val api: ApiService = ApiService.create()
    data class ResponseUid(val uid: String)

    suspend fun insert(habit: HabitModel, new: Boolean = true) {
        val habitToServer = HabitConverter.HabitToHabitFromServer(habit)
        if (new)
        {
            habitToServer.uid = null
        }
        val response = api.putHabit(habitToServer)
        if (response.isSuccessful and new) {
            val responseBody = response.body()?.string()
            val gson = Gson()
            val responseUid = gson.fromJson(responseBody, ResponseUid::class.java)
            habit.id = responseUid.uid
        } else {
            // Обработка ошибки при выполнении запроса
        }
        Log.d("habit", habit.toString())
        habitDao.insertHabit(habit)
    }

    suspend fun getHabitsSortedByPriority(desc: Boolean): List<HabitModel> =
        habitDao.getHabitsSortedByPriority(desc)

    suspend fun getHabitsFilteredByName(name: String): List<HabitModel> =
        habitDao.getHabitsFilteredByName(name)
    suspend fun getHabitsFromApi() =
        api.getHabits()
}
