package com.kamal.newsapplication.repository.implementation

import com.kamal.newsapplication.api.SourcesItem
import com.kamal.newsapplication.NetworkStatus
import com.kamal.newsapplication.repository.SourcesRepository
import com.kamal.newsapplication.repository.dataSources.SourcesOfflineDataSource
import com.kamal.newsapplication.repository.dataSources.SourcesOnlineDataSource

class SourcesRepositoryImplementation(val sourcesOnlineDataSource: SourcesOnlineDataSource,
                                      val sourcesOfflineDataSource: SourcesOfflineDataSource,
                                      val networkstatus: NetworkStatus) :SourcesRepository{
    override suspend fun getSources(): List<SourcesItem?> {
        //If network available
        if(networkstatus.isOnline())
        {
            //get data from API
           val sources =  sourcesOnlineDataSource.getSources()
            //then cache data in local dataBase
            cacheSources(sources)
            return sources
        }
        return sourcesOfflineDataSource.getSources()
    }

    override suspend fun cacheSources(data:List<SourcesItem?>) {
        sourcesOfflineDataSource.cacheSources(data)
    }
}