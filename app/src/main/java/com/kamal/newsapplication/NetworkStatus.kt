package com.kamal.newsapplication

import android.content.Context
import android.net.ConnectivityManager
import androidx.core.content.ContextCompat.getSystemService


interface NetworkStatus {
    fun isOnline():Boolean
}
class NetworkStatusImplementation(var context: Context):NetworkStatus
{
    companion object{
        var network:NetworkStatus?=null
        fun init(context: Context)
        {
            network = NetworkStatusImplementation(context)
        }
        fun getInstance():NetworkStatus
        {
            return network!!
        }
    }
    override fun isOnline(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        return cm!!.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
    }
}