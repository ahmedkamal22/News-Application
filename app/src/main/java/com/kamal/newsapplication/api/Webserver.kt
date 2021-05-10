package com.kamal.newsapplication.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Webserver {

    @GET("sources")
    fun getNewsSources(@Query("apiKey")apiKey:String,
                       @Query("language")language:String,
                       @Query("country")country:String
    ):Call<SourcesResponse>
}