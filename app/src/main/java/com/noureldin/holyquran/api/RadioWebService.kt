package com.noureldin.holyquran.api

import com.noureldin.holyquran.model.Response
import retrofit2.Call
import retrofit2.http.GET

interface RadioWebService {

    @GET("radios")
    fun getRadio(): Call<Response>
}