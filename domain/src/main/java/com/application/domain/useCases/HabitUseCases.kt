package com.application.domain.useCases

import android.util.Log
import com.application.data.mapper.HabitConverter
import com.application.data.model.HabitModel
import com.application.data.model.RequestDone
import com.application.data.model.ResponseUid
import com.application.domain.useCases.repository.AppRepository
import com.google.gson.Gson
import java.time.OffsetDateTime
import java.time.ZoneOffset
import javax.inject.Inject

class HabitUseCases @Inject constructor(private val appRepository: AppRepository) {

    suspend fun init() {
        val habitsFromApi = appRepository.getHabitsFromApi()
        val habitFromDb = appRepository.selectAllHabitsFromDB();
        habitsFromApi.forEach { habit ->
            if (!habitFromDb.contains(habit)) {
                appRepository.insertHabitIntoDB(habit)
            }
        }

        habitFromDb.forEach{habit ->
            if (!habitsFromApi.contains(habit)){
                appRepository.deleteHabitFromDB(habit)
                insert(habit)
            }
        }
    }

    suspend fun habitDone(habit: HabitModel){
        val date = OffsetDateTime.now(ZoneOffset.UTC).toEpochSecond().toInt()
        appRepository.postHabitDone(RequestDone(date,habit.id))
        habit.doneDates.add(date)
        appRepository.insertHabitIntoDB(habit)
    }

    suspend fun delete(habit: HabitModel) {
        appRepository.deleteHabitFromApi(ResponseUid(habit.id))
        appRepository.deleteHabitFromDB(habit)
    }

    suspend fun insert(habit: HabitModel, new: Boolean = true) {
        val habitToServer = HabitConverter.HabitToHabitFromServer(habit)
        if (new) {
            habitToServer.uid = null
        }
        val response = appRepository.putHabitIntoApi(habitToServer)
        if (response.isSuccessful && new) {
            val responseBody = response.body()?.string()
            val gson = Gson()
            val responseUid = gson.fromJson(responseBody, ResponseUid::class.java)
            habit.id = responseUid.uid
        } else {
            // Обработка ошибки при выполнении запроса
        }
        Log.d("habit", habit.toString())
        appRepository.insertHabitIntoDB(habit)
    }

    suspend fun getHabitsSortedByPriority(desc: Boolean): List<HabitModel> =
        appRepository.getHabitsSortedByPriorityFromDb(desc)

    suspend fun getHabitsFilteredByName(name: String): List<HabitModel> =
        appRepository.getHabitsFilteredByNameFromDb(name)
    suspend fun getAllHabitsFromDb(): List<HabitModel> =
    appRepository.selectAllHabitsFromDB()
}
