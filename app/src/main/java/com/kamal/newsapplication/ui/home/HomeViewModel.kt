package com.kamal.newsapplication.ui.home

import android.content.DialogInterface
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kamal.newsapplication.api.*
import com.kamal.newsapplication.ui.Constants
import kotlinx.android.synthetic.main.activity_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel:ViewModel() {
    val sourcesLiveData = MutableLiveData<List<SourcesItem?>?>()
    val newsLiveData = MutableLiveData<List<ArticlesItem?>?>()
    val progressLiveData = MutableLiveData<Boolean>(false)
    val messageLiveData = MutableLiveData<String>()
     fun getSources() {
        ApiManager.getApies().getNewsSources(apiKey = Constants.ApiKey,language = "en",country = "us")
            .enqueue(object: Callback<SourcesResponse> {
                override fun onResponse(
                    call: Call<SourcesResponse>,
                    response: Response<SourcesResponse>
                ) {
                    progressLiveData.value = false
                    if(response.isSuccessful)
                    {
                        sourcesLiveData.value = response.body()?.sources
                    }
                    else
                    {
                        messageLiveData.value = "Network Error ${response.body()?.message?:""}"
                    }
                }

                override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
                    progressLiveData.value = false
                    messageLiveData.value = "Check Your Internet Connection ${t.localizedMessage}"
                }
            })
    }
    fun getNews(sourceId: String?) {
        progressLiveData.value = true
        newsLiveData.value = null
        ApiManager.getApies().getAllNews(apiKey = Constants.ApiKey,
            language = "en",sources = sourceId?:"")
            .enqueue(object :Callback<NewsResponse>{
                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>
                ) {
                    progressLiveData.value = false
                    if(response.isSuccessful)
                    {
                        newsLiveData.value = response.body()?.articles
                    }
                    else
                    {
                        messageLiveData.value = "Network Error ${response.body()?.message?:""}"
                    }
                }

                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    progressLiveData.value = false
                    messageLiveData.value = "Network Error ${t.localizedMessage}"
                }
            })
    }
}