package com.kamal.newsapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kamal.newsapplication.R
import com.kamal.newsapplication.api.ArticlesItem
import com.kamal.newsapplication.databinding.LayoutNewsBinding

class NewsAdapter(var items:List<ArticlesItem?>?):RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding:LayoutNewsBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.layout_news,parent,
        false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val newsItem = items?.get(position)
        holder.bind(newsItem!!)
    }

    override fun getItemCount(): Int {
        return items?.size?:0
    }

    fun changeData(items: List<ArticlesItem?>?)
    {
        this.items = items
        notifyDataSetChanged()
    }
    class ViewHolder(val itemBinding:LayoutNewsBinding):RecyclerView.ViewHolder(itemBinding.root)
    {
        fun bind(newsList:ArticlesItem)
        {
            itemBinding.newsList=newsList
            itemBinding.invalidateAll() //for refreshing layout of view
        }
    }
}