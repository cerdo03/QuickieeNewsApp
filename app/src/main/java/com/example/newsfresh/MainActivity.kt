package com.example.newsfresh

import android.net.Uri
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent.Builder
import androidx.viewpager2.widget.ViewPager2
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity() : AppCompatActivity(), newsItemClicked, CardStackListener
     {
//    private lateinit var mAdapter: newsListAdapter
    private lateinit var viewPager2Adapter: viewPagerAdapter
    private lateinit var viewPager2:ViewPager2
//    private lateinit var adapter:viewPagerAdapter
//    private lateinit var layoutManager: CardStackLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        fetchData()
//        mAdapter= newsListAdapter(this)
//        recyclerView.adapter=mAdapter
        viewPager2 = viewpager
       
        viewPager2Adapter = viewPagerAdapter(this)
        fetchData()
        viewPager2.adapter=viewPager2Adapter

//        viewPager2.apply {
//            offscreenPageLimit=3
//            setPageTransformer(ViewPagerCardTransformer(3))
//        }


//        viewPager2.registerOnPageChangeCallback(object : OnPageChangeCallback() {
//            // This method is triggered when there is any scrolling activity for the current page
//            override fun onPageScrolled(
//                position: Int,
//                positionOffset: Float,
//                positionOffsetPixels: Int
//            ) {
//                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
//            }
//
//            // triggered when you select a new page
//            override fun onPageSelected(position: Int) {
//                super.onPageSelected(position)
//            }
//
//            // triggered when there is
//            // scroll state will be changed
//            override fun onPageScrollStateChanged(state: Int) {
//                super.onPageScrollStateChanged(state)
//            }
//        })
        viewPager2.offscreenPageLimit=3
        viewPager2.setPageTransformer(ViewPagerCardTransformer())
//            layoutManager = CardStackLayoutManager(this,this).apply{
//                setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
//                setOverlayInterpolator(LinearInterpolator())
//            }
//        fetchData()
//        layoutManager.setVisibleCount(3)
//        layoutManager.setDirections(Direction.VERTICAL)
//        layoutManager.setCanScrollHorizontal(false)
//        layoutManager.setSwipeThreshold(0.2f)
//        layoutManager.setScaleInterval(0.95f)
//        layoutManager.setTranslationInterval(100.0f)
//        stack_view.layoutManager = layoutManager
//        adapter = viewPagerAdapter(this)
//        stack_view.adapter = adapter
//        stack_view.itemAnimator.apply {
//            if (this is DefaultItemAnimator) {
//                supportsChangeAnimations = false
//            }
//        }




    }
    private fun fetchData(){
        val url = "https://newsapi.org/v2/top-headlines?country=in&apiKey=55f0f9f1dd6c4aaa9d33720dc1485c17"
        val jsonObjectRequest = object :JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener{
                val newsJsonArray = it.getJSONArray("articles")
                val newsArray =  ArrayList<news>()
                for(i in 0 until newsJsonArray.length()){
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val news= news(
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("author"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage"),
                        newsJsonObject.getString("content")
                    )
                    newsArray.add(news)
                }
                viewPager2Adapter.update(newsArray)

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


    override fun onItemClick(item: news) {
        val builder = Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.url))
    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {

    }

    override fun onCardSwiped(direction: Direction?) {


    }

    override fun onCardRewound() {

    }

    override fun onCardCanceled() {

    }

    override fun onCardAppeared(view: View?, position: Int) {

    }

    override fun onCardDisappeared(view: View?, position: Int) {

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
        private const val MIN_SCALE = 0.75f

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


