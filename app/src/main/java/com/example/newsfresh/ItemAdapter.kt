package com.example.newsfresh

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class ItemAdapter(private val context: Context, val dataset: List<topic_item>, val listener: ImageItemClicked):RecyclerView.Adapter<ItemAdapter.ItemViewHolder>(){
    class ItemViewHolder(private val view: View):RecyclerView.ViewHolder(view){
        val imageView: ImageView = view.findViewById(R.id.image_view)
        val textView: TextView = view.findViewById(R.id.text_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.topicitem, parent, false)

        return ItemViewHolder(adapterLayout)

    }

    override fun onBindViewHolder(holder: ItemAdapter.ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.imageView.setImageResource(item.getURI())
        holder.textView.text=item.getTopicName()
        holder.imageView.setOnClickListener{
            listener.onItemClicked(dataset[position])
        }
        holder.textView.setOnClickListener{
            listener.onItemClicked((dataset[position]))
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

}
interface ImageItemClicked{
    fun onItemClicked(item: topic_item)
}