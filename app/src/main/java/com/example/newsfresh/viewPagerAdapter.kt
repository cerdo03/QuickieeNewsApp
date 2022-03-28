package com.example.newsfresh

import android.content.Context
import android.content.Intent
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.internal.NavigationMenu

import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter





class viewPagerAdapter(private val context: Context,private val listener: IviewPagerAdapter):RecyclerView.Adapter<viewPagerAdapter.newsViewHolder>()

{
    val allNews = ArrayList<news>()
    val map = hashMapOf<news,Boolean>()
    var onItemClick: ((news) -> Unit)? = null
        inner class newsViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val title = itemView.findViewById<TextView>(R.id.title)
        val content = itemView.findViewById<TextView>(R.id.content)
        val image = itemView.findViewById<ImageView>(R.id.image)
        val menu = itemView.findViewById<io.github.yavski.fabspeeddial.FabSpeedDial>(R.id.menu)
        val btm = itemView.findViewById<TextView>(R.id.btm)

//        init {
//            itemView.setOnClickListener {
//                onItemClick?.invoke(allNews[adapterPosition])
//            }
//
//        }




        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): newsViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_news,parent,false)

        val viewHolder= newsViewHolder(view)
        viewHolder.btm.setOnClickListener{
            listener.onLinkClicked(allNews[viewHolder.adapterPosition])
        }
        view.setOnClickListener(View.OnClickListener { view ->
            Toast.makeText(context,(allNews.size-viewHolder.adapterPosition-1).toString()+" Unread News",Toast.LENGTH_SHORT).show()
        })
//        view.setOnLongClickListener(object : View.OnLongClickListener {
//            override fun onLongClick(v: View): Boolean {
//
//                return true
//            }
//        })
//        viewHolder.menu.setOnClickListener(object : View.OnClickListener {
//             override fun onClick(v: View?) {
//                viewHolder.menu.toggle()
//            }
//        })
//


        viewHolder.menu.setMenuListener(object : SimpleMenuListenerAdapter() {

            override fun onMenuItemSelected(menuItem: MenuItem?): Boolean {
                if(menuItem.toString()=="Share"){
                    listener.shareNews(allNews[viewHolder.adapterPosition])
                }
                return super.onMenuItemSelected(menuItem)
            }

        })


        return viewHolder
    }

    override fun onBindViewHolder(holder: newsViewHolder, position: Int) {
        val currentNews=allNews[position]
        holder.title.text=preprocess(currentNews.title)

        holder.content.text=currentNews.content
//         postRequest(currentNews.url,holder)
//        postRequest(currentNews.url,holder)



        holder.btm.text="Click here to read full story"
        Glide.with(holder.itemView.context).load(currentNews.imageurl).into(holder.image)
    }

    override fun getItemCount(): Int {
        return allNews.size
    }
    fun update(newList: ArrayList<news>){
//        allNews.clear()
        var size =  allNews.size
        for(i in size until newList.size){
            allNews.add(newList[i])
            map[newList[i]] = false
        }

//        allNews.addAll(newList)
        notifyDataSetChanged()
    }
    fun preprocess(str:String): String {

        return str.replace("\\s+".toRegex(), " ").trim { it <= ' ' }

    }





}



interface IviewPagerAdapter{
    fun onLinkClicked(news:news)
    fun shareNews(news:news)


}


