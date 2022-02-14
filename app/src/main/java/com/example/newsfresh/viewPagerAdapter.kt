package com.example.newsfresh

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import org.json.JSONObject

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
        allNews.clear()

        allNews.addAll(newList)
        notifyDataSetChanged()
    }
    fun preprocess(str:String): String {

        return str.replace("\\s+".toRegex(), " ").trim { it <= ' ' }

    }
//    fun postRequest(newsArray:ArrayList<news>): ArrayList<news> {
//             val queue: RequestQueue = Volley.newRequestQueue(context)
//             val url = "https://articlesummary.herokuapp.com/api"
//             Log.d("lund",newsArray.size.toString())
//            var responseJsonObjectData=""
//             for(i in 0..newsArray.size-1){
//
//                 val jobject= JSONObject()
//                 jobject.put("url",newsArray.get(i).url)
//
//                 val jsonObjectRequest = object : JsonObjectRequest(
//                     Request.Method.POST,
//                     url,    jobject,
//                     Response.Listener
//                     {
//                         responseJsonObjectData = it.getString("result")
//                         newsArray.set(i,news(newsArray.get(i).title,newsArray.get(i).author,newsArray.get(i).url,
//                             newsArray.get(i).imageurl,responseJsonObjectData))
//                         Log.d("choot",newsArray.toString())
//
//                     },
//                     Response.ErrorListener
//                     {
//
//                     }){
//                     override fun getHeaders(): MutableMap<String, String> {
//                         val head: MutableMap<String, String> = HashMap()
//                         head["Authorization"] = "cerdoboss"
//                         return head
//                     }
//                 }
//                 queue.add(jsonObjectRequest)
//
//
//             }
//            return  newsArray
//
//
//         }

    fun postRequest(articleLink:String,holder: newsViewHolder){
        val queue: RequestQueue = Volley.newRequestQueue(context)
        val url = "https://articlesummary.herokuapp.com/api"


            val jobject= JSONObject()
            jobject.put("url",articleLink)

            val jsonObjectRequest = object : JsonObjectRequest(
                Request.Method.POST,
                url,    jobject,
                Response.Listener
                {
                    val responseJsonObjectData = it.getString("result")
                    Log.d("choot",responseJsonObjectData)
                    if(responseJsonObjectData.length>10)
                    holder.content.text=responseJsonObjectData
                    else
                        holder.content.text="lund"
                },
                Response.ErrorListener
                {

                }){
                override fun getHeaders(): MutableMap<String, String> {
                    val head: MutableMap<String, String> = HashMap()
                    head["Authorization"] = "cerdoboss"
                    return head
                }
            }
            queue.add(jsonObjectRequest)

    }




}



interface IviewPagerAdapter{
    fun onLinkClicked(news:news)
    fun longPress()
}

