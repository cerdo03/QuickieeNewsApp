package com.example.newsfresh

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class viewPagerAdapter(private val context: Context,private val listener: IviewPagerAdapter):RecyclerView.Adapter<viewPagerAdapter.newsViewHolder>()
{
    val allNews = ArrayList<news>()
    inner class newsViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val title = itemView.findViewById<TextView>(R.id.title)
        val content = itemView.findViewById<TextView>(R.id.content)
        val image = itemView.findViewById<ImageView>(R.id.image)
        val btm = itemView.findViewById<TextView>(R.id.btm)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): newsViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_news,parent,false)
        val viewHolder= newsViewHolder(view)
        viewHolder.btm.setOnClickListener{
            listener.onLinkClicked(allNews[viewHolder.adapterPosition])
        }


        return viewHolder
    }

    override fun onBindViewHolder(holder: newsViewHolder, position: Int) {
        val currentNews=allNews[position]
        holder.title.text=preprocess(currentNews.title)
        holder.content.text=preprocess(currentNews.content)
        holder.btm.text="Click here to read full story"
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
    fun preprocess(str:String): String {

        return str.replace("\\s+".toRegex(), " ").trim { it <= ' ' }

    }


}



interface IviewPagerAdapter{
    fun onLinkClicked(news:news)
    fun longPress()
}

