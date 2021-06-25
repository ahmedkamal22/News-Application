package com.kamal.newsapplication

import android.app.Application
import com.kamal.newsapplication.dataBase.NewsDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        NewsDatabase.init(this)
        NetworkStatusImplementation.init(this)
    }
}