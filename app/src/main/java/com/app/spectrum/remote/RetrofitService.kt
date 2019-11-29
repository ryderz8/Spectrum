package com.app.spectrum.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by amresh on 27/11/2019
 */
class RetrofitService {

    private val retrofit = Retrofit.Builder().baseUrl("https://newsapi.org/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <S> cteateService(serviceClass: Class<S>): S {
        return retrofit.create(serviceClass)
    }
}