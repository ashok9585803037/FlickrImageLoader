package com.xome.flickrloader.views.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.xome.flickrloader.R

class Splashscreen : AppCompatActivity() {
private val delayTime=2000L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splashscreen)

    }

    private fun navigateHome() {
        val intent = Intent(this, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

    override fun onResume() {
        Handler(Looper.myLooper()!!).postDelayed(
            {
                navigateHome()
            },
            delayTime
        )
        super.onResume()
    }
}