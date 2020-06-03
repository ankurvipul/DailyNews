package com.demo.dailynews.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.demo.dailynews.R

class SplashActivity : AppCompatActivity() {

    private var handler: Handler? = null
    private var runnable: Runnable? = null
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val companyView = findViewById<TextView>(R.id.companyView)
        companyView.text = getString(R.string.company)

        handler = Handler()
        runnable = Runnable {
            val context = this@SplashActivity
            /*Checks whether user is logged in or not.
            * Constants.IS_LOGIN - true is Logged in else false */
            startActivity(Intent(context, MainActivity::class.java))
            finish()
        }
        handler!!.postDelayed(runnable!!, 2000)
    }

    override fun onPause() {
        super.onPause()
        onBackPressed()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (handler != null && runnable != null) {
            handler!!.removeCallbacks(runnable!!)
        }
    }
}
