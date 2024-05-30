//package com.application.hw2.api
//
//import android.util.Log
//import okhttp3.Interceptor
//import okhttp3.Response
//
//class RetryingInterceptor(private val tryCnt: Int = 3, private val baseInterval: Long = 5000L) : Interceptor {
//
//    override fun intercept(chain: Interceptor.Chain): Response {
//        return process(chain, attempt = 1)
//    }
//
//    private fun process(chain: Interceptor.Chain, attempt: Int): Response {
//        var response: Response?
//        try {
//            val request = chain.request()
//            response = chain.proceed(request)
//            if (attempt < tryCnt && !response.isSuccessful) {
//                Log.d("retry", attempt.toString())
//                return delayedAttempt(chain, response, attempt)
//            }
//            return response
//        } catch (e: Exception) {
//            Log.e("error",e.toString())
//            throw e
//        }
//    }
//
//    private fun delayedAttempt(
//        chain: Interceptor.Chain,
//        response: Response?,
//        attempt: Int,
//    ): Response {
//        response?.body?.close()
//        Thread.sleep(baseInterval * attempt)
//        return process(chain, attempt = attempt + 1)
//    }
//}