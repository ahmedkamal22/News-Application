package com.kamal.newsapplication.api

import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {
    @Provides
    fun provideWebServer(retrofit: Retrofit):Webserver
    {
        return retrofit.create(Webserver::class.java)
    }
    @Provides
    fun provideGsoConverterFactory():GsonConverterFactory
    {
        return GsonConverterFactory.create()
    }
    @Provides
    fun provideOkHttpLoggingInterceptor():HttpLoggingInterceptor
    {
        val interceptor = HttpLoggingInterceptor(object: HttpLoggingInterceptor.Logger {
        override fun log(message: String) {
            Log.e("Api",message)
        }
    })
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return interceptor
    }
    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor):OkHttpClient
    {
        return OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
    }
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient,gsonConverterFactory: GsonConverterFactory):Retrofit
    {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(gsonConverterFactory)
            .build()
    }
}