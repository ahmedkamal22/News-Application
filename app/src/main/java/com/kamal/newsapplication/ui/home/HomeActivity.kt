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
import com.kamal.newsapplication.databinding.ActivityHomeBinding
import com.kamal.newsapplication.ui.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding,HomeViewModel>() ,TabLayout.OnTabSelectedListener{
    lateinit var newsAdapter: NewsAdapter
    override fun intializeViewModel(): HomeViewModel {
        return ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    override fun getLayoutID(): Int {
        return R.layout.activity_home
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRecycler()
        observeOnFunctions()
        viewModel.getSources()
    }

    private fun observeOnFunctions() {
        viewModel.messageLiveData.observe(this, Observer {
            showDialig("Internet Error",
                            it,
                            posActionName = "Ok",posAction = DialogInterface.OnClickListener { dialog, which ->
                                dialog.dismiss()
                                finish()
                            },isCancallable = false)
        })
        viewModel.progressLiveData.observe(this, Observer {
            visible->
            if(visible)
                viewDataBinding.progress.visibility = View.VISIBLE
            else
                viewDataBinding.progress.visibility = View.VISIBLE
        })
        viewModel.sourcesLiveData.observe(this, Observer {
            newsList->
            showSourceInTabLayout(newsList)
        })
        viewModel.newsLiveData.observe(this, Observer {
                newsList->
            showNewsInRecyclerView(newsList)
        })
    }
    private fun setRecycler() {
        newsAdapter = NewsAdapter(null)
       viewDataBinding.newsRecyclerView.adapter = newsAdapter
    }


    private fun showSourceInTabLayout(sources: List<SourcesItem?>?) {
        sources?.forEach {
            item->
            val tab = viewDataBinding.tabLayout.newTab()
            tab.setText(item?.name)
            tab.setTag(item)
            viewDataBinding.tabLayout.addTab(tab)
        }
        viewDataBinding.tabLayout.addOnTabSelectedListener(this)
        viewDataBinding.tabLayout.getTabAt(0)?.select()
    }
    override fun onTabSelected(tab: TabLayout.Tab?) {
        var item = tab?.tag as SourcesItem
        viewModel.getNews(item.id)
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {

    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
        var item = tab?.tag as SourcesItem
        viewModel.getNews(item.id)
    }


    private fun showNewsInRecyclerView(newsList: List<ArticlesItem?>?) {
        newsAdapter.changeData(newsList)
    }
}