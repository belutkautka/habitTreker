package com.application.domain.useCases.repository

import com.application.data.model.HabitFromServer
import com.application.data.model.HabitModel
import com.application.data.model.RequestDone
import com.application.data.model.ResponseUid
import retrofit2.Response
import okhttp3.ResponseBody

interface AppRepository {

    suspend fun selectAllHabitsFromDB() : List<HabitModel>
    suspend fun insertHabitIntoDB(habit: HabitModel)
    suspend fun deleteHabitFromDB(habit : HabitModel)
    suspend fun getHabitsSortedByPriorityFromDb(desc:Boolean): List<HabitModel>
    suspend fun getHabitsFilteredByNameFromDb(name: String): List<HabitModel>

    suspend fun putHabitIntoApi(habit : HabitFromServer) : Response<ResponseBody>
    suspend fun getHabitsFromApi() : List<HabitModel>
    suspend fun postHabitDone(postDone : RequestDone)
    suspend fun deleteHabitFromApi(uid : ResponseUid)
    suspend fun postHabitsDone(done : RequestDone)
}