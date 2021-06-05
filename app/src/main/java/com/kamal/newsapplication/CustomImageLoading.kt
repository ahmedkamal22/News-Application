package com.kamal.newsapplication

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("setImageUrl")
fun setImageFromUrl(image:ImageView,url:String)
{
    Glide.with(image)
        .load(url)
        .into(image)
}