package com.application.data_module.api

import com.google.gson.annotations.SerializedName

data class RequestDone(
    val date: Int,
    @SerializedName("habit_uid")
    val habitUId: String
)