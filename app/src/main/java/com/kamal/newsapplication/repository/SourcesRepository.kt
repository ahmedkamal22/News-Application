package com.kamal.newsapplication.repository

import com.kamal.newsapplication.api.SourcesItem

interface SourcesRepository {
   suspend fun getSources():List<SourcesItem?>
    suspend fun cacheSources(data:List<SourcesItem?>)
}