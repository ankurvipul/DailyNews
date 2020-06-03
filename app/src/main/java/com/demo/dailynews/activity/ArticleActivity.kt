package com.demo.dailynews.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.dailynews.ArticlesAdapter
import com.demo.dailynews.R
import com.demo.dailynews.model.ApiResponse
import com.demo.dailynews.network.EndPoints
import com.demo.dailynews.network.RetrofitClient
import com.google.android.material.appbar.AppBarLayout
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_article.*
import retrofit2.Call
import retrofit2.Callback

class ArticleActivity : AppCompatActivity(), AppBarLayout.OnOffsetChangedListener {

    private val percentageToShowTitleAtToolbar = 0.9f
    private var mIsTheTitleVisible = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)
        backImageView.setOnClickListener {
            onBackPressed()
        }

        recyclerViewArticles.setHasFixedSize(true)
        recyclerViewArticles.isNestedScrollingEnabled =  false
        val mLayoutManager = LinearLayoutManager(this@ArticleActivity)
        recyclerViewArticles.layoutManager = mLayoutManager

        val categoryApi = intent.extras!!.getString("url")
        val categoryTitle = intent.extras!!.getString("title")
        val categoryImage = intent.extras!!.getInt("image")
        Log.v("Image",categoryImage.toString())

        categoryTitleView.text=categoryTitle
        titleViewAfterCollapse.text=categoryTitle

        Picasso.with(this).load(categoryImage).into(collapseImageView)

        val endPoints = RetrofitClient(this).client!!.create<EndPoints>(EndPoints::class.java)
        val call = endPoints.getApiOneNews(categoryApi!!)
        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: retrofit2.Response<ApiResponse>) {
                Log.v("URLs","${response.raw()}")

                val categoriesResponse = response.body()
                val responseStatus = categoriesResponse!!.status
                val responseData = categoriesResponse.articles

                if (responseStatus=="ok") {
                    shimmer_view_container!!.stopShimmerAnimation()
                    shimmer_view_container!!.visibility = View.GONE
                    val adapter = ArticlesAdapter(this@ArticleActivity, responseData!!)
                    recyclerViewArticles!!.adapter = adapter
                }
                else{
                    shimmer_view_container!!.stopShimmerAnimation()
                    shimmer_view_container!!.visibility = View.GONE
                }

            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
            Log.v("Error:",t.message!!)
                Toast.makeText(this@ArticleActivity, t.message, Toast.LENGTH_SHORT).show()
            }
        })

        mainAppbar!!.addOnOffsetChangedListener(this)
    }
        override fun onResume() {
        super.onResume()
        shimmer_view_container!!.startShimmerAnimation()
    }
    override fun onPause() {
        shimmer_view_container!!.stopShimmerAnimation()
        super.onPause()
    }
    override fun onOffsetChanged(appBarLayout: AppBarLayout, offset: Int) {
        val maxScroll = appBarLayout.totalScrollRange
        val percentage = Math.abs(offset).toFloat() / maxScroll.toFloat()
        handleToolbarTitleVisibility(percentage)
    }

    private fun handleToolbarTitleVisibility(percentage: Float) {
        if (percentage >= percentageToShowTitleAtToolbar) {
            if (!mIsTheTitleVisible) {

                Log.v("Collasping","True")
                titleViewAfterCollapse.visibility = View.VISIBLE
                mIsTheTitleVisible = true
            }
        } else {
            if (mIsTheTitleVisible) {
                Log.v("Collasping","False")

                titleViewAfterCollapse.visibility = View.GONE
                mIsTheTitleVisible = false
            }
        }
    }

}
