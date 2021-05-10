package com.kamal.newsapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kamal.newsapplication.R
import com.kamal.newsapplication.api.ArticlesItem
import kotlinx.android.synthetic.main.layout_news.view.*

class NewsAdapter(var items:List<ArticlesItem?>?):RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_news,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val adb = items?.get(position)
        holder.newsDate.setText(adb?.publishedAt)
        holder.newsHeader.setText(adb?.title)
        holder.newsDescription.setText(adb?.description)

        Glide
            .with(holder.itemView)
            .load(adb?.urlToImage)
            .into(holder.newsImage)
    }

    override fun getItemCount(): Int {
        return items?.size?:0
    }

    fun changeData(items: List<ArticlesItem?>?)
    {
        this.items = items
        notifyDataSetChanged()
    }
    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)
    {
        val newsDate:TextView = itemView.data
        val newsHeader:TextView = itemView.title
        val newsImage:ImageView = itemView.image
        val newsDescription:TextView = itemView.desc
    }
}