package com.kamal.newsapplication.repository.implementation

import com.kamal.newsapplication.api.SourcesItem
import com.kamal.newsapplication.api.Webserver
import com.kamal.newsapplication.dataBase.NewsDatabase
import com.kamal.newsapplication.repository.dataSources.SourcesOfflineDataSource
import com.kamal.newsapplication.repository.dataSources.SourcesOnlineDataSource
import com.kamal.newsapplication.ui.Constants
import javax.inject.Inject

class SourcesOnlineDataSourceImplementation @Inject constructor(val webserver: Webserver):SourcesOnlineDataSource
{
    override suspend fun getSources(): List<SourcesItem?> {
        val sourcesResponse = webserver.getNewsSources(Constants.ApiKey,"en","us")
        return sourcesResponse.sources!!
    }
}
class SourcesOfflineDataSourceImplementation @Inject constructor(val dataBase:NewsDatabase):SourcesOfflineDataSource
{
    override suspend fun getSources(): List<SourcesItem?> {
        val sources = dataBase.sourcesDao().getNewsSource()
        return sources
    }

    override suspend fun cacheSources(data: List<SourcesItem?>) {
        dataBase.sourcesDao().cacheSources(data)
    }
}