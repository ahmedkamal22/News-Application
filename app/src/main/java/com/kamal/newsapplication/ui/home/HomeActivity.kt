package com.kamal.newsapplication.ui.home


import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.islami.base.BaseActivity
import com.google.android.material.tabs.TabLayout
import com.kamal.newsapplication.R
import com.kamal.newsapplication.adapters.NewsAdapter
import com.kamal.newsapplication.api.*
import com.kamal.newsapplication.ui.Constants
import kotlinx.android.synthetic.main.activity_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : BaseActivity() ,TabLayout.OnTabSelectedListener{
    lateinit var newsAdapter: NewsAdapter
    lateinit var homeViewModel: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        setRecycler()
        observeOnFunctions()
        homeViewModel.getSources()
    }

    private fun observeOnFunctions() {
        homeViewModel.messageLiveData.observe(this, Observer {
            showDialig("Internet Error",
                            it,
                            posActionName = "Ok",posAction = DialogInterface.OnClickListener { dialog, which ->
                                dialog.dismiss()
                                finish()
                            },isCancallable = false)
        })
        homeViewModel.progressLiveData.observe(this, Observer {
            visible->
            if(visible)
                progress.visibility = View.VISIBLE
            else
                progress.visibility = View.GONE
        })
        homeViewModel.sourcesLiveData.observe(this, Observer {
            newsList->
            showSourceInTabLayout(newsList)
        })
        homeViewModel.newsLiveData.observe(this, Observer {
                newsList->
            showNewsInRecyclerView(newsList)
        })
    }
    private fun setRecycler() {
        newsAdapter = NewsAdapter(null)
        news_recycler_view.adapter = newsAdapter
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
        homeViewModel.getNews(item.id)
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {

    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
        var item = tab?.tag as SourcesItem
        homeViewModel.getNews(item.id)
    }


    private fun showNewsInRecyclerView(newsList: List<ArticlesItem?>?) {
        newsAdapter.changeData(newsList)
    }
}