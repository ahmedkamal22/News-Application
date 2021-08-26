package com.kamal.newsapplication.dataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kamal.newsapplication.api.SourcesItem

@Dao
interface SourcesDao {
    @Query("select * from sourcesitem")
   suspend fun getNewsSource():List<SourcesItem>

   @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun cacheSources(data:List<SourcesItem?>?)
}