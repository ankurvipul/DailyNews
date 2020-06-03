package com.demo.dailynews.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.dailynews.Constants
import com.demo.dailynews.R
import com.demo.dailynews.adapter.CategoriesAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var categories = mutableListOf("All articles about Bitcoin from the last month",
        "Top business headlines in the US right now", "All articles mentioning Apple from yesterday",
        "Top headlines from TechCrunch right now", "All articles published by the Wall Street Journal in the last 6 months.")
    private var categoryImages = mutableListOf(
        R.drawable.bit_coin,
        R.drawable.usa,
        R.drawable.apple,
        R.drawable.tech_crunch,
        R.drawable.wall_street
    )
    @SuppressLint("SimpleDateFormat")
    private val formatter = SimpleDateFormat("yyyy-MM-dd")
    private val date = Date()
    private val currentDate: String = formatter.format(date)// getting current to pass it bitcoin api

    private var categoriesApi = mutableListOf(
        Constants.BASE_URL +"everything?q=bitcoin&from=$currentDate&sortBy=publishedAt&apiKey="+ Constants.API_KEY,
        Constants.BASE_URL +"top-headlines?country=us&category=business&apiKey=d00b8142f86046edbb6bfa5434607a43",
        Constants.BASE_URL +"everything?q=apple&from=2020-01-07&to=2020-01-07&sortBy=popularity&apiKey=d00b8142f86046edbb6bfa5434607a43",
        Constants.BASE_URL +"top-headlines?sources=techcrunch&apiKey=d00b8142f86046edbb6bfa5434607a43",
        Constants.BASE_URL +"everything?domains=wsj.com&apiKey=d00b8142f86046edbb6bfa5434607a43")

    private var categoryAdapter:CategoriesAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerViewMain.setHasFixedSize(true)
        recyclerViewMain.isNestedScrollingEnabled =  false
        val mLayoutManager = LinearLayoutManager(this@MainActivity)
        recyclerViewMain.layoutManager = mLayoutManager
        categoryAdapter = CategoriesAdapter(this, categories,categoryImages,categoriesApi)
        recyclerViewMain.adapter = categoryAdapter


    }
}
