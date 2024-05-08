package com.application.hw2.db

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import com.application.hw2.api.ApiService
import com.application.hw2.api.RequestDone
import com.application.hw2.api.ResponseUid
import com.application.hw2.model.HabitConverter
import com.application.hw2.model.HabitModel
import com.google.gson.Gson
import java.time.OffsetDateTime
import java.time.ZoneOffset

class HabitRepository(private val habitDao: HabitsDao) {
    var allHabits: LiveData<List<HabitModel>> = habitDao.getAllHabits()
    private val api: ApiService = ApiService.create()

    suspend fun initFromApi() {
        val habitsFromApi = api.getHabits()
        habitsFromApi.forEach { habitFromServer ->
            val habit = HabitConverter.HabitFromServerToHabit(habitFromServer)
            if (!allHabits.value!!.contains(habit)) {
                habitDao.insertHabit(habit)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun habitDone(habit: HabitModel){
        val date = OffsetDateTime.now(ZoneOffset.UTC).toEpochSecond().toInt()
        api.postHabitDone(RequestDone(date,habit.id))
        habit.doneDates.add(date)
        habitDao.insertHabit(habit)
    }

    suspend fun delete(habit: HabitModel) {
        api.deleteHabit(ResponseUid(habit.id))
        habitDao.deleteHabit(habit)
    }

    suspend fun insert(habit: HabitModel, new: Boolean = true) {
        val habitToServer = HabitConverter.HabitToHabitFromServer(habit)
        if (new) {
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
