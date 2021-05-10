package com.kamal.newsapplication.api

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class ApiManager {
    companion object
    {
       private var retrofit:Retrofit?=null
        private fun getInstance():Retrofit
        {
            val logging = HttpLoggingInterceptor(object: HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                    Log.e("Api",message)
                }
            })
            if(retrofit==null)
            {
                val interceptor = HttpLoggingInterceptor()
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
                val client =
                    OkHttpClient.Builder().addInterceptor(interceptor).build()
                //create
                retrofit = Retrofit.Builder()
                    .client(client)
                    .baseUrl("https://newsapi.org/v2/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!
        }
        fun getApies():Webserver
        {
            return getInstance().create(Webserver::class.java)
        }
    }
}