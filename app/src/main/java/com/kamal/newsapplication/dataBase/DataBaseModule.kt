package com.kamal.newsapplication.dataBase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {
    @Provides
   fun provideDataBase():NewsDatabase
   {
       return NewsDatabase.getInstance()
   }
}