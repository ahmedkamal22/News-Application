package com.kamal.newsapplication.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kamal.newsapplication.api.SourcesItem

@Database(entities = [SourcesItem::class],version = 1,exportSchema = false)
abstract class NewsDatabase:RoomDatabase() {
    abstract fun sourcesDao():SourcesDao
    companion object
    {
       private var newsDatabase:NewsDatabase?=null
        fun init(context: Context)
        {
            newsDatabase = Room.databaseBuilder(context,NewsDatabase::class.java,"News_DB")
                .fallbackToDestructiveMigration()
                .build()
        }
        fun getInstance():NewsDatabase
        {
            return newsDatabase!!
        }
    }
}