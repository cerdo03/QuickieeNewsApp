package com.example.newsfresh

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager2.widget.ViewPager2
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_display.*
import org.json.JSONObject


class DisplayActivity() : AppCompatActivity(),IviewPagerAdapter

{
    //    private lateinit var mAdapter: newsListAdapter
    private lateinit var viewPager2Adapter: viewPagerAdapter
    private lateinit var viewPager2:ViewPager2
    lateinit var drawerLayout: DrawerLayout
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    internal var x1: Float = 0.toFloat()
    internal var x2: Float = 0.toFloat()
    internal var y1: Float = 0.toFloat()
    internal var y2: Float = 0.toFloat()
    companion object{
        const val newUrl="newUrl"
    }
    var ss =false

//    private lateinit var adapter:viewPagerAdapter
//    private lateinit var layoutManager: CardStackLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        getSupportActionBar()?.setTitle("");
        progressBar.visibility=View.VISIBLE


        viewPager2 = viewpager
        viewPager2Adapter = viewPagerAdapter(this,this)


        val url = intent.getStringExtra(newUrl)
        if (url != null) {
            fetchData(url)
        }



        viewPager2.adapter=viewPager2Adapter
//        viewPager2Adapter.onItemClick={
//            news ->
//            Toast.makeText(this,"Clicked",Toast.LENGTH_SHORT).show()
//        }

        drawerLayout = drawer_Layout
        actionBarDrawerToggle =
            ActionBarDrawerToggle(this, drawerLayout, com.example.newsfresh.R.string.nav_open, com.example.newsfresh.R.string.nav_close)

        val navView : NavigationView = nav_view

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        this.drawerLayout.addDrawerListener(actionBarDrawerToggle!!)
        actionBarDrawerToggle!!.syncState()

        // to make the Navigation drawer icon always appear on the action bar

        // to make the Navigation drawer icon always appear on the action bar
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.facts -> {
                    Toast.makeText(applicationContext,"clicked",Toast.LENGTH_SHORT).show()
                }
                R.id.settings -> {
                    Toast.makeText(applicationContext,"clicked",Toast.LENGTH_SHORT).show()
                }
                R.id.bookmarks -> {
                    Toast.makeText(applicationContext,"clicked",Toast.LENGTH_SHORT).show()
                }
                R.id.explore -> {
                    openExp()
                }

            }
            true
        }


        viewPager2.offscreenPageLimit=15
        viewPager2.setPageTransformer(ViewPagerCardTransformer())




    }



    fun openExp() {
        val intent = Intent(this,MainActivity2::class.java)
        startActivityForResult(intent,0)
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle!!.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }

    private fun fetchData(url: String ="https://newsapi.org/v2/top-headlines?c" +
            "ountry=in&apiKey=55f0f9f1dd6c4aaa9d33720dc1485c17" ){

        val jsonObjectRequest = object :JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener{
                val newsJsonArray = it.getJSONArray("articles")
                var newsArray=ArrayList<news>()
                for(i in 0 until newsJsonArray.length()) {
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
//                    postRequest(newsJsonObject.getString("url"))
                    val news = news(
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("author"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage"),
                        "",
                    )
                    if(news.title!="null" && news.url!="null" && news.imageurl!="null")
                        newsArray.add(news)

                }
                postRequest(newsArray)


            },
            Response.ErrorListener{
            }
        )
        {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0"
                return headers
            }
        }


        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

    }
    fun postRequest(newsArray:ArrayList<news>): ArrayList<news> {
        val n=ArrayList<news>()
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val url = "https://articlesummary.herokuapp.com/api"
        Log.d("lund",newsArray.size.toString())
        for(i in 0 until newsArray.size){

            val jobject= JSONObject()
            jobject.put("url",newsArray.get(i).url)

            val jsonObjectRequest = object : JsonObjectRequest(
                Request.Method.POST,
                url,    jobject,
                Response.Listener
                {
                    val responseJsonObjectData = it.getString("result")
//                         newsArray.set(i,news(newsArray.get(i).title,newsArray.get(i).author,newsArray.get(i).url,
//                             newsArray.get(i).imageurl,responseJsonObjectData))
                    n.add(news(newsArray.get(i).title,newsArray.get(i).author,newsArray.get(i).url,
                        newsArray.get(i).imageurl,responseJsonObjectData))
                    progressBar.visibility=View.GONE
                    viewPager2Adapter.update(n)

                },
                Response.ErrorListener
                {
                    Log.d("randi",it.toString())
                }){
                override fun getHeaders(): MutableMap<String, String> {
                    val head: MutableMap<String, String> = HashMap()
                    head["Authorization"] = "cerdoboss"
                    return head
                }
            }
            queue.add(jsonObjectRequest)
        }
        return n

    }



    override fun onLinkClicked(news: news) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(news.url))
        startActivity(browserIntent)
    }
    }








class ViewPagerCardTransformer() : ViewPager2.PageTransformer {
    companion object {

        private const val DEFAULT_TRANSLATION_X = .0f
        private const val DEFAULT_TRANSLATION_FACTOR = 1.2f

        private const val SCALE_FACTOR = .14f
        private const val DEFAULT_SCALE = 1f

        private const val ALPHA_FACTOR = .3f
        private const val DEFAULT_ALPHA = 1f
        private const val offscreenPageLimit=3
        private const val MIN_SCALE = 0.9f

    }

    override fun transformPage(view: View, position: Float) {
        view.apply {
            val pageWidth = width
            val pageHeight = height
            when {
                position < -1 -> { // [-Infinity,-1)
                    // This page is way off-screen to the left.
                    alpha = 0f
                }
                position <= 0 -> { // [-1,0]
                    // Use the default slide transition when moving to the left page
                    alpha = 1f
                    translationY = 0f
                    translationZ = 0f
                    scaleX = 1f
                    scaleY = 1f
                }
                position <= 1 -> { // (0,1]
                    // Fade the page out.
                    alpha = 1 - position

                    // Counteract the default slide transition
                    translationY = pageHeight * -position
                    // Move it behind the left page
                    translationZ = -1f

                    // Scale the page down (between MIN_SCALE and 1)
                    val scaleFactor = (MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position)))
                    scaleX = scaleFactor
                    scaleY = scaleFactor
                }
                else -> { // (1,+Infinity]
                    // This page is way off-screen to the right.
                    alpha = 0f
                }
            }
        }
    }

}


