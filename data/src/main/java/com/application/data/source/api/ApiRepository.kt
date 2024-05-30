package com.application.data.source.api

import com.application.data.mapper.HabitConverter
import com.application.data.model.HabitFromServer
import com.application.data.model.HabitModel
import com.application.data.model.RequestDone
import com.application.data.model.ResponseUid
import com.application.data.restful.ApiService

open class ApiRepository(private val api: ApiService){
    suspend fun getHabits(): List<HabitFromServer> = api.getHabits()
    suspend fun  postHabitsDone(done: RequestDone) = api.postHabitDone(done)
    suspend fun putHabit(habit: HabitFromServer) = api.putHabit(habit)
    suspend fun deleteHabit(uid: ResponseUid) = api.deleteHabit(uid)
}