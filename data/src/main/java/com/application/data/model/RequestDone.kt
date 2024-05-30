package com.application.data.model

import com.google.gson.annotations.SerializedName

data class RequestDone(
    val date: Int,
    @SerializedName("habit_uid")
    val habitUId: String
)