package com.org.covidcare.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.org.covidcare.R

/**
 * Created by ishwari s on 6/17/2020.
 */
class SplashActivity : AppCompatActivity() {
    private val splashTimeOut: Long = 1000 // 1 sec

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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