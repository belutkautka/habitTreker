package com.application.data.source.api

import com.application.data.restful.ApiService
import com.application.domain.useCases.model.HabitFromServer
import com.application.domain.useCases.model.RequestDone
import com.application.domain.useCases.model.ResponseUid
import javax.inject.Inject

open class ApiRepository @Inject constructor(private val api: ApiService){
    suspend fun getHabits(): List<HabitFromServer> = api.getHabits()
    suspend fun  postHabitsDone(done: RequestDone) = api.postHabitDone(done)
    suspend fun putHabit(habit: HabitFromServer) = api.putHabit(habit)
    suspend fun deleteHabit(uid: ResponseUid) = api.deleteHabit(uid)
}