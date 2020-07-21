package com.org.covidcare.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Window
import com.org.covidcare.R

/**
 * Created by ishwari s on 6/17/2020.
 */
class SplashActivity : AppCompatActivity() {
    private val splashTimeOut: Long = 2000 // 2 sec

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            // This method will be executed once the timer is over
            // Start your app main activity
            startActivity(Intent(this, DashboardActivity::class.java))

            // close this activity
            finish()
        }, splashTimeOut)
    }
}