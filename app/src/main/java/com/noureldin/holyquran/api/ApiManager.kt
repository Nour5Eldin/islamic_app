package com.noureldin.holyquran.api

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object ApiManager {
    const val BASE_URL= "https://mp3quran.net/api/v3/"
    val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }
    private val okHttpClient = OkHttpClient.Builder().addInterceptor(getInterceptor()).build()

    private fun getInterceptor():HttpLoggingInterceptor{
        return HttpLoggingInterceptor{message->
            Log.e(TAG, "getInterceptor: message=$message" )
        }
    }
    private const val TAG = "ApiManager"

    val radioWebService = retrofit.create<RadioWebService>()
}