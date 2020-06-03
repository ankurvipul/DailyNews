package com.demo.dailynews



import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.demo.dailynews.activity.ArticleActivity
import com.demo.dailynews.activity.FullArticleActivity
import com.demo.dailynews.model.ApiResponse
import com.demo.dailynews.model.ApiSourceName
import com.demo.dailynews.model.Article
import com.squareup.picasso.Picasso
import retrofit2.Callback
import java.util.*
class ArticlesAdapter(
    mContext: ArticleActivity,
    articleData: List<Article>
) :
    RecyclerView.Adapter<ArticlesAdapter.ViewHolder>() {
    var context: ArticleActivity = mContext
    private var responseSourceName = ArrayList<String>()
    private var articleData = ArrayList<Article>()
    private var lastPosition = -1

    init {
        this.articleData = articleData as ArrayList<Article>

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.adapter_articles, parent, false))
    }

    override fun getItemCount(): Int {
        return articleData.size
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val n=articleData[position].source

        holder.sourceName!!.text =n!!.name
        holder.sourceTitle!!.text = articleData[position].title
        holder.sourceDesc!!.text = articleData[position].description
        holder.sourceAuthor!!.text = articleData[position].author

        // check if image is empty or null and try- catch for handling exception
        try {
        when {
            articleData[position].urlToImage!!.isNotEmpty() -> {
                Picasso.with(context).load(articleData[position].urlToImage).into(holder.sourseImg!!)
            }
            articleData[position].urlToImage.equals(null) -> {
                holder.sourseImg!!.setImageResource(R.drawable.no_image)
            }
            else -> {
                holder.sourseImg!!.setImageResource(R.drawable.no_image)
            }
        }
        }
        catch (e:Exception)
        {
            e.printStackTrace()
        }

        holder.cardViewMain!!.setOnClickListener {

            val intent= Intent(context,FullArticleActivity::class.java)

            intent.putExtra("sourceName",n.name)
            intent.putExtra("sourceTitle",articleData[position].title)
            intent.putExtra("sourceDesc",articleData[position].description)
            intent.putExtra("sourceAuthor",articleData[position].author)
            intent.putExtra("url",articleData[position].url)
            intent.putExtra("urlToImage",articleData[position].urlToImage)
            intent.putExtra("publishedAt",articleData[position].publishedAt)
            intent.putExtra("content",articleData[position].content)
            context.startActivity(intent)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var sourceName: TextView? = null
        var sourceTitle: TextView? = null
        var sourceDesc: TextView? = null
        var sourceAuthor: TextView? = null
        var sourseImg: ImageView? = null
        var publishedDate: TextView? = null
        var cardViewMain: CardView? = null

        init {
            sourceName = itemView.findViewById(R.id.sourceName) as TextView
            sourceTitle = itemView.findViewById(R.id.sourceTitle) as TextView
            sourceDesc = itemView.findViewById(R.id.sourceDesc) as TextView
            sourceAuthor = itemView.findViewById(R.id.sourceAuthor) as TextView
            sourseImg = itemView.findViewById(R.id.sourceImage) as ImageView
            cardViewMain = itemView.findViewById(R.id.cardViewMain) as CardView
         }
    }
}