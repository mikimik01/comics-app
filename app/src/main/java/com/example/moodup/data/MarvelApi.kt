package com.example.moodup.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MarvelApi {
    private const val BASE_URL = "httpS://gateway.marvel.com/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val retrofitService: MarvelApiService by lazy {
        retrofit.create(MarvelApiService::class.java)
    }
}