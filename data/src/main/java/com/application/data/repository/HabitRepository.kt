package com.application.data.repository

import com.application.data.mapper.HabitConverter
import com.application.data.model.HabitFromServer
import com.application.data.model.HabitModel
import com.application.data.model.RequestDone
import com.application.data.model.ResponseUid
import com.application.data.source.api.ApiRepository
import com.application.data.source.database.DbRepository

import com.application.domain.useCases.repository.AppRepository
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class HabitRepository @Inject constructor (private val api: ApiRepository, private val db: DbRepository) : AppRepository {
    override suspend fun selectAllHabitsFromDB(): List<HabitModel> {
        return db.getAllHabits()
    }

    override suspend fun insertHabitIntoDB(habit: HabitModel) {
        db.insertHabit(habit)
    }

    override suspend fun deleteHabitFromDB(habit: HabitModel) {
        db.deleteHabit(habit)
    }

    override suspend fun getHabitsSortedByPriorityFromDb(desc:Boolean): List<HabitModel> {
        return db.getHabitsSortedByPriority(desc)
    }

    override suspend fun getHabitsFilteredByNameFromDb(name: String): List<HabitModel> {
        return db.getHabitsFilteredByName(name)
    }

    override suspend fun putHabitIntoApi(habit: HabitFromServer): Response<ResponseBody> {
       return api.putHabit(habit)
    }

    override suspend fun getHabitsFromApi(): List<HabitModel> {
        return api.getHabits().map { habit -> HabitConverter.HabitFromServerToHabit(habit) }
    }

    override suspend fun postHabitDone(postDone: RequestDone) {
        return api.postHabitsDone(postDone)
    }

    override suspend fun deleteHabitFromApi(uid: ResponseUid) {
        api.deleteHabit(uid)
    }

    override suspend fun postHabitsDone(done: RequestDone) {
       api.postHabitsDone(done)
    }

}