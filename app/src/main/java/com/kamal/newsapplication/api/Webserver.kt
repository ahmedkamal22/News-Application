package com.kamal.newsapplication.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Webserver {

    @GET("sources")
    suspend fun getNewsSources(@Query("apiKey")apiKey:String,
                       @Query("language")language:String,
                       @Query("country")country:String
    ):SourcesResponse

    @GET("everything")
    fun getAllNews(@Query("apiKey")apiKey:String,
                       @Query("language")language:String,
                       @Query("sources")sources:String
    ):Call<NewsResponse>
}