package com.kamal.newsapplication.repository

import com.kamal.newsapplication.NetworkStatus
import com.kamal.newsapplication.NetworkStatusImplementation
import com.kamal.newsapplication.api.Webserver
import com.kamal.newsapplication.dataBase.NewsDatabase
import com.kamal.newsapplication.repository.dataSources.SourcesOfflineDataSource
import com.kamal.newsapplication.repository.dataSources.SourcesOnlineDataSource
import com.kamal.newsapplication.repository.implementation.SourcesOfflineDataSourceImplementation
import com.kamal.newsapplication.repository.implementation.SourcesOnlineDataSourceImplementation
import com.kamal.newsapplication.repository.implementation.SourcesRepositoryImplementation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class SourcesRepositoryModule {
    @Provides
    fun provideSourcesOnlineDataSources(webserver: Webserver):SourcesOnlineDataSource
    {
        return SourcesOnlineDataSourceImplementation(webserver)
    }
    @Provides
    fun provideSourcesOfflineDataSources(database: NewsDatabase):SourcesOfflineDataSource
    {
        return SourcesOfflineDataSourceImplementation(database)
    }
    @Provides
    fun provideNetworkStatus():NetworkStatus
    {
        return NetworkStatusImplementation.getInstance()
    }
    @Provides
    fun provideSourcesRepository(sourcesOnlineDataSource: SourcesOnlineDataSource,
    sourcesOfflineDataSource: SourcesOfflineDataSource,
    networkStatus: NetworkStatus):SourcesRepository
    {
        return SourcesRepositoryImplementation(sourcesOnlineDataSource,sourcesOfflineDataSource,networkStatus)
    }
}