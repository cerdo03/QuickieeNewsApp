package com.example.newsfresh

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder

class viewPagerAdapter(private val context: Context):RecyclerView.Adapter<viewPagerAdapter.newsViewHolder>() {
    val allNews = ArrayList<news>()
    inner class newsViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val title = itemView.findViewById<TextView>(R.id.title)
        val content = itemView.findViewById<TextView>(R.id.content)
        val image = itemView.findViewById<ImageView>(R.id.image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): newsViewHolder {
        val viewHolder= newsViewHolder(LayoutInflater.from(context).inflate(R.layout.item_news,parent,false))
        return viewHolder
    }

    override fun onBindViewHolder(holder: newsViewHolder, position: Int) {
        val currentNews=allNews[position]
        holder.title.text=currentNews.title
        holder.content.text=currentNews.content
        Glide.with(holder.itemView.context).load(currentNews.imageurl).into(holder.image)
    }

    override fun getItemCount(): Int {
        return allNews.size
    }
    fun update(newList: ArrayList<news>){
        allNews.clear()
        allNews.addAll(newList)
        notifyDataSetChanged()
    }
}

