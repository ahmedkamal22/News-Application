package com.kamal.newsapplication.ui.home


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kamal.newsapplication.api.*
import com.kamal.newsapplication.dataBase.NewsDatabase
import com.kamal.newsapplication.repository.SourcesRepository
import com.kamal.newsapplication.repository.dataSources.SourcesOfflineDataSource
import com.kamal.newsapplication.repository.dataSources.SourcesOnlineDataSource
import com.kamal.newsapplication.repository.implementation.SourcesOfflineDataSourceImplementation
import com.kamal.newsapplication.repository.implementation.SourcesOnlineDataSourceImplementation
import com.kamal.newsapplication.repository.implementation.SourcesRepositoryImplementation
import com.kamal.newsapplication.ui.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val sourcesRepository: SourcesRepository, val webserver: Webserver):ViewModel() {
    val sourcesLiveData = MutableLiveData<List<SourcesItem?>?>()
    val newsLiveData = MutableLiveData<List<ArticlesItem?>?>()
    val progressLiveData = MutableLiveData<Boolean>(false)
    val messageLiveData = MutableLiveData<String>()


    fun getSources() {
        progressLiveData.value = true
        viewModelScope.launch {
            try {
                progressLiveData.value = false
                val sources = sourcesRepository.getSources()
                sourcesLiveData.value = sources
            } catch (exception: Exception) {
                progressLiveData.value = false
                messageLiveData.value = exception.localizedMessage
            }
        }
    }
        //        ApiManager.getApies().getNewsSources(apiKey = Constants.ApiKey,language = "en",country = "us")
//            .enqueue(object: Callback<SourcesResponse> {
//                override fun onResponse(
//                    call: Call<SourcesResponse>,
//                    response: Response<SourcesResponse>
//                ) {
//                    progressLiveData.value = false
//                    if(response.isSuccessful)
//                    {
//                        sourcesLiveData.value = response.body()?.sources
//                    }
//                    else
//                    {
//                        messageLiveData.value = "Network Error ${response.body()?.message?:""}"
//                    }
//                }
//
//                override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
//                    progressLiveData.value = false
//                    messageLiveData.value = "Check Your Internet Connection ${t.localizedMessage}"
//                }
//            })
//    }
        fun getNews(sourceId: String?) {
            progressLiveData.value = true
            newsLiveData.value = null
            webserver.getAllNews(
                apiKey = Constants.ApiKey,
                language = "en", sources = sourceId ?: ""
            )
                .enqueue(object : Callback<NewsResponse> {
                    override fun onResponse(
                        call: Call<NewsResponse>,
                        response: Response<NewsResponse>
                    ) {
                        progressLiveData.value = false
                        if (response.isSuccessful) {
                            newsLiveData.value = response.body()?.articles
                        } else {
                            messageLiveData.value =
                                "Network Error ${response.body()?.message ?: ""}"
                        }
                    }

                    override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                        progressLiveData.value = false
                        messageLiveData.value = "Network Error ${t.localizedMessage}"
                    }
                })
        }
    }