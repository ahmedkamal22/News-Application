package com.kamal.newsapplication.repository.dataSources

import com.kamal.newsapplication.api.SourcesItem

interface SourcesOnlineDataSource
{
   suspend fun getSources():List<SourcesItem?>

}
interface SourcesOfflineDataSource
{
    suspend fun getSources():List<SourcesItem?>
    suspend fun cacheSources(data:List<SourcesItem?>)
}