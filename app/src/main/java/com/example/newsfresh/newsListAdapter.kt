package com.example.newsfresh

import android.view.LayoutInflater
import android.view.OnReceiveContentListener
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class newsListAdapter(val listener: newsItemClicked): RecyclerView.Adapter<newsViewHolder>() {
    private val items: ArrayList<news> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): newsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news,parent,false)
        val viewHolder=newsViewHolder(view)
        view.setOnClickListener{
            listener.onItemClick(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: newsViewHolder, position: Int) {
        val currentItem = items[position]
        holder.titleView.text = currentItem.title
        holder.author.text= currentItem.author
        Glide.with(holder.itemView.context).load(currentItem.imageurl).into(holder.image)
    }

    override fun getItemCount(): Int {
        return items.size
    }
    fun updateNews(updatesNews: ArrayList<news>){
        items.clear()
        items.addAll(updatesNews)
        notifyDataSetChanged()
    }

}
class newsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val titleView: TextView=itemView.findViewById(R.id.title)
    val image: ImageView=itemView.findViewById(R.id.image)
    val author: TextView=itemView.findViewById(R.id.content)
}

interface newsItemClicked{
    fun onItemClick(item:news)
}
