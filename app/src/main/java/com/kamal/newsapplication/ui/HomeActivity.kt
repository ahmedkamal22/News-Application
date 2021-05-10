package com.kamal.newsapplication.ui


import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import com.example.islami.base.BaseActivity
import com.google.android.material.tabs.TabLayout
import com.kamal.newsapplication.R
import com.kamal.newsapplication.adapters.NewsAdapter
import com.kamal.newsapplication.api.*
import kotlinx.android.synthetic.main.activity_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : BaseActivity() ,TabLayout.OnTabSelectedListener{
    lateinit var newsAdapter: NewsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setRecycler()
        getSources()
    }

    private fun setRecycler() {
        newsAdapter = NewsAdapter(null)
        news_recycler_view.adapter = newsAdapter
    }

    private fun getSources() {
        ApiManager.getApies().getNewsSources(apiKey = Constants.ApiKey,language = "en",country = "us")
            .enqueue(object: Callback<SourcesResponse> {
                override fun onResponse(
                    call: Call<SourcesResponse>,
                    response: Response<SourcesResponse>
                ) {
                    progress.visibility = View.GONE
                    if(response.isSuccessful)
                    {
                        showSourceInTabLayout(response.body()?.sources)
                    }
                    else
                    {
                        showDialig("Server Error 505",
                            "Network Error ${response.body()?.message?:""}",
                            posActionName = "Ok",posAction = DialogInterface.OnClickListener { dialog, which ->
                                dialog.dismiss()
                            },isCancallable = false)
                    }
                }

                override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
                    progress.visibility = View.GONE
                    showDialig("Error 404","Network Error ${t.localizedMessage}",
                        posActionName = "Retry",posAction = DialogInterface.OnClickListener { dialog, which ->
                            call.enqueue(this)
                            dialog.dismiss()
                        },isCancallable = false)
                }
            })
    }

    private fun showSourceInTabLayout(sources: List<SourcesItem?>?) {
        sources?.forEach {
            item->
            val tab = tabLayout.newTab()
            tab.setText(item?.name)
            tab.setTag(item)
            tabLayout.addTab(tab)
        }
        tabLayout.addOnTabSelectedListener(this)
        tabLayout.getTabAt(0)?.select()
    }
    override fun onTabSelected(tab: TabLayout.Tab?) {
        var item = tab?.tag as SourcesItem
        getNews(item.id)
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {

    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
        var item = tab?.tag as SourcesItem
        getNews(item.id)
    }

    private fun getNews(sourceId: String?) {
        newsAdapter.changeData(null)
        progress.visibility = View.VISIBLE
        ApiManager.getApies().getAllNews(apiKey = Constants.ApiKey,
        language = "en",sources = sourceId?:"")
            .enqueue(object :Callback<NewsResponse>{
                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>
                ) {
                    progress.visibility = View.GONE
                    if(response.isSuccessful)
                    {
                        showNewsInRecyclerView(response.body()?.articles)
                    }
                    else
                    {
                        showDialig("Server Error 505",
                            "Network Error ${response.body()?.message?:""}",
                            posActionName = "Ok",posAction = DialogInterface.OnClickListener { dialog, which ->
                                dialog.dismiss()
                            },isCancallable = false)
                    }
                }

                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    progress.visibility = View.GONE
                    showDialig("Error 404","Network Error ${t.localizedMessage}",
                        posActionName = "Retry",posAction = DialogInterface.OnClickListener { dialog, which ->
                            call.enqueue(this)
                            dialog.dismiss()
                        },isCancallable = false)
                }
            })
    }

    private fun showNewsInRecyclerView(newsList: List<ArticlesItem?>?) {
        newsAdapter.changeData(newsList)
    }
}