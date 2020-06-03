package com.demo.dailynews.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.demo.dailynews.R
import com.google.android.material.appbar.AppBarLayout
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_full_article.*
import java.text.SimpleDateFormat


class FullArticleActivity : AppCompatActivity(), AppBarLayout.OnOffsetChangedListener {
    private val percentageToShowTitleAtToolbar = 0.9f
    private var mIsTheTitleVisible = false
    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_article)

        backImageView.setOnClickListener {
            onBackPressed()
        }
        val sourceN = intent.extras!!.getString("sourceName")
        val sourceT = intent.extras!!.getString("sourceTitle")
        val publishedAt = intent.extras!!.getString("publishedAt")
        val sourceAuthorName = intent.extras!!.getString("sourceAuthor")
        val sourceDescription = intent.extras!!.getString("sourceDesc")
        val sourceCont = intent.extras!!.getString("content")
        val sourceImage = intent.extras!!.getString("urlToImage")
        val sourceUrl = intent.extras!!.getString("url")

        sourceName.text = sourceN
        sourceTitle.text = sourceT
        sourceDesc.text = sourceDescription
        sourceAuthor.text = sourceAuthorName
        sourceContent.text = sourceCont
        sourceTitleAfterCollapse.text= sourceN
        try {
        if (sourceImage!!.isNotEmpty()) {
            Picasso.with(this).load(sourceImage).into(collapseSourceImage)
        }
        else{
            collapseSourceImage.setImageResource(R.drawable.no_image)
        }
        }
        catch (e:Exception)
        {
            e.printStackTrace()
        }
        //change the date format
        val isoDateFormat =
            SimpleDateFormat("yyyy-mm-dd'T'hh:mm:ss'Z'")
        val dateFormat =
            SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aaa")
        val date = isoDateFormat.parse(publishedAt!!)
        publishedDate.text = dateFormat.format(date!!)

        //read full story in mobile browser
        readFullStoryLayout.setOnClickListener {
            val browserIntent =
                Intent(Intent.ACTION_VIEW, Uri.parse(sourceUrl))
            startActivity(browserIntent)
        }
        mainAppbarFullArticle!!.addOnOffsetChangedListener(this)
    }
    override fun onOffsetChanged(appBarLayout: AppBarLayout, offset: Int) {
        val maxScroll = appBarLayout.totalScrollRange
        val percentage = Math.abs(offset).toFloat() / maxScroll.toFloat()
        handleToolbarTitleVisibility(percentage)
    }

    private fun handleToolbarTitleVisibility(percentage: Float) {
        if (percentage >= percentageToShowTitleAtToolbar) {
            if (!mIsTheTitleVisible) {

                sourceTitleAfterCollapse.visibility = View.VISIBLE
                mIsTheTitleVisible = true
            }
        } else {
            if (mIsTheTitleVisible) {
                sourceTitleAfterCollapse.visibility = View.GONE
                mIsTheTitleVisible = false
            }
        }
    }


}
