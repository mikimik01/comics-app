package com.example.moodup.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelApiService {
    @GET("v1/public/comics")
    fun getComics(
        @Query("ts") ts: String,
        @Query("apikey") apiKey: String,
        @Query("hash") hash: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Call<ComicDataWrapper>

    @GET("v1/public/comics")
    fun searchComics(
        @Query("ts") ts: String,
        @Query("apikey") apiKey: String,
        @Query("hash") hash: String,
        @Query("titleStartsWith") query: String,
        @Query("limit") limit: Int = 10
    ): Call<ComicDataWrapper>
}
