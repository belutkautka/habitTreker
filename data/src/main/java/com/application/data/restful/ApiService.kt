package com.application.data.restful

import com.application.domain.useCases.model.HabitFromServer
import com.application.domain.useCases.model.RequestDone
import com.application.domain.useCases.model.ResponseUid
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface ApiService {

    @GET("api/habit")
    suspend fun getHabits(
    ) :List<HabitFromServer>

    @PUT("api/habit")
    suspend fun putHabit(
        @Body habit: HabitFromServer
    ): Response<ResponseBody>

    @HTTP(method = "DELETE", path = "api/habit", hasBody = true)
    suspend fun deleteHabit(
        @Body uid: ResponseUid
    )

    @POST("api/habit_done")
    suspend fun postHabitDone(
        @Body postDone: RequestDone
    )

    companion object Factory {

        const val API_BASE_URL = "https://droid-test-server.doubletapp.ru/"
//        const val API_BASE_URL = "https://droid-test-server.doubletapp.ru/test/"
        const val CONTENT_TYPE = "application/json"
        const val API_KEY = "f11649ac-44a0-41e2-bcc2-da1bbcf68b64"

        fun create(): ApiService {

            val client = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .addInterceptor(RetryingInterceptor())
                .addInterceptor { chain ->
                    chain.proceed(chain.request().newBuilder()
                        .addHeader("accept", CONTENT_TYPE)
                        .addHeader("Authorization", API_KEY)
                        .addHeader("Content-Type", CONTENT_TYPE)
                        .build())
                }
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build()


            val gson = GsonBuilder()
                .registerTypeAdapter(
                    HabitFromServer::class.java,
                    HabitFromServer.HabitJsonDeserializer()
                )
                .create()

            val retrofit = Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}