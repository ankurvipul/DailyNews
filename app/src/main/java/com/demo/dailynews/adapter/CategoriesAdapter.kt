package com.demo.dailynews.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.demo.dailynews.ArticlesAdapter
import com.demo.dailynews.R
import com.demo.dailynews.activity.ArticleActivity
import com.demo.dailynews.model.ApiResponse
import com.demo.dailynews.network.EndPoints
import com.demo.dailynews.network.RetrofitClient
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback

class CategoriesAdapter(
    context: Context,
    categoriesList: List<String>,
    categoriesImageList: List<Int>,
    categoriesApi: MutableList<String>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var context: Context? = null
    var categoriesL: List<String>? = null
    private var categoriesImg: List<Int>? = null
    var categoriesApis: List<String>? = null

    init {
        this.categoriesL = categoriesList
        this.categoriesImg = categoriesImageList
        this.categoriesApis = categoriesApi
        this.context = context
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder: ViewHolder = holder as ViewHolder
        val categoryList = categoriesL!![position]
        val categoryImg = categoriesImg!![position]
        val categoryApi = categoriesApis!![position]
        viewHolder.categoriesTitle?.text = categoryList
        Picasso.with(context).load(categoryImg).into(viewHolder.categoriesImages)

        holder.cardView!!.setOnClickListener {
            val intent=Intent(context,ArticleActivity::class.java)
            intent.putExtra("url",categoryApi)
            intent.putExtra("title",categoryList)
            intent.putExtra("image",categoryImg)
            context!!.startActivity(intent)
            Log.v("Apis", "$categoryImg  $categoryApi")

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.categories_layout, parent, false))
    }


    inner class ViewHolder(row: View) : RecyclerView.ViewHolder(row) {

        var cardView: CardView? = null
        var categoriesTitle: TextView? = null
        var categoriesImages: ImageView? = null

        init {

            this.cardView = row.findViewById(R.id.cardViewCategories) as CardView
            this.categoriesTitle = row.findViewById(R.id.categoriesTitle) as TextView
            this.categoriesImages = row.findViewById(R.id.categoriesImage) as ImageView

        }
    }

    override fun getItemCount(): Int {
        return categoriesL!!.size
    }
}