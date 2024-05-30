package com.application.domain.useCases.model

import com.google.gson.*
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.lang.reflect.Type

data class HabitFromServer(
    var uid : String?,
    var title: String,
    var description: String,
    var priority: Int,
    var type: Int,
    var count: Int,
    var frequency: Int,
    var color: Int,
    var date: Int,
    var doneDates: MutableList<Int>
) : Serializable {

    data class DoneDates(
        @SerializedName("done_dates")
        var done_dates: MutableList<Int>
    )

    class HabitJsonDeserializer : JsonDeserializer<HabitFromServer> {
        override fun deserialize(
            json: JsonElement,
            typeOfT: Type?,
            context: JsonDeserializationContext?
        ): HabitFromServer {
            val list = GsonBuilder().create()
                .fromJson<DoneDates>(
                    json.asJsonObject,
                    DoneDates::class.java
                ).done_dates
            return HabitFromServer(
                uid = json.asJsonObject.get("uid").asString,
                title = json.asJsonObject.get("title").asString,
                description = json.asJsonObject.get("description").asString,
                priority = json.asJsonObject.get("priority").asInt,
                type = json.asJsonObject.get("type").asInt,
                count = json.asJsonObject.get("count").asInt,
                frequency = json.asJsonObject.get("frequency").asInt,
                color = json.asJsonObject.get("color").asInt,
                date = json.asJsonObject.get("date").asInt,
                doneDates = list.toMutableList()
            )
        }
    }
}
