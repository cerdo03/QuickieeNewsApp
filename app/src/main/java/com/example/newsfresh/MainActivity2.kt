package com.example.newsfresh

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity2 : AppCompatActivity(),ImageItemClicked {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val dataset = dataSource().loadTopics()
        gridLay(dataset as MutableList<topic_item>)
    }

    fun search(view: View) {
        val keyword = search_bar.editableText.toString()
        val url = "https://newsapi.org/v2/everything?" +
                "q=" + keyword +
                "&sortBy=popularity&" +
                "apiKey=55f0f9f1dd6c4aaa9d33720dc1485c17"
        val intent = Intent(this,DisplayActivity::class.java)
        intent.putExtra(DisplayActivity.newUrl,url)
        startActivity(intent)
    }
    fun gridLay(dataset:MutableList<topic_item>){
        GridLayoutManager(
            this, // context
            3, // span count
            RecyclerView.VERTICAL, // orientation
            false // reverse layout,
        ).apply {
            // specify thne layout manager for recycler view
            recyclerView.layoutManager = this
        }
        /* finally, data bind the recycler view with adapter */
        recyclerView.adapter = ItemAdapter(this,dataset,this)

    }


    override fun onItemClicked(item: topic_item) {

            val intent = Intent(this,DisplayActivity::class.java)
            intent.putExtra(DisplayActivity.newUrl,item.getUrl())
            startActivity(intent)


    }
}