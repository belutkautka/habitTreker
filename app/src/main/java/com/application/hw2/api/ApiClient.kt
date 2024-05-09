package com.application.hw2.api


import android.os.Handler
import android.os.Looper
import android.util.Log
import com.application.hw2.model.HabitFromServer
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.lang.reflect.Type

class ApiClient(client: OkHttpClient, url:String, gson: Gson) {
    val api: ApiService

    init {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(createRetryCallAdapterFactory())
            .baseUrl(url)
            .client(client)
            .build()

        api = retrofit.create(ApiService::class.java)
    }

    private fun createRetryCallAdapterFactory(): CallAdapter.Factory {
        return object : CallAdapter.Factory() {
            override fun get(
                returnType: Type,
                annotations: Array<out Annotation>,
                retrofit: Retrofit
            ): CallAdapter<*, *>? {
                return object : CallAdapter<Any, Call<Any>> {
                    override fun responseType(): Type = returnType

                    override fun adapt(call: Call<Any>): Call<Any> {
                        return object : Call<Any> {
                            override fun enqueue(callback: Callback<Any>) {
                                call.clone().enqueue(object : Callback<Any> {
                                    override fun onResponse(
                                        call: Call<Any>,
                                        response: Response<Any>
                                    ) {
                                        Log.d("reaponce", "true")
                                    }

                                    override fun onFailure(call: Call<Any>, t: Throwable) {
                                        val handler = Handler(Looper.getMainLooper())

                                        if (t is IOException) {
                                            Log.d("retry", "yes")
                                            handler.postDelayed(
                                                { call.clone().enqueue(this) },
                                                1000L
                                            )
                                        } else {
                                            Log.d("retry", t.toString())
                                        }
                                    }
                                })
                            }

                            override fun clone(): Call<Any> {
                                return call.clone()
                            }

                            override fun execute(): Response<Any> {
                                return call.execute()
                            }

                            override fun request(): Request {
                                return call.request()
                            }

                            override fun timeout(): Timeout {
                                return call.timeout()
                            }

                            override fun isExecuted(): Boolean {
                                return call.isExecuted
                            }

                            override fun cancel() {
                                call.cancel()
                            }

                            override fun isCanceled(): Boolean {
                                return call.isCanceled
                            }
                        }
                    }
                }
            }
        }
    }
}

