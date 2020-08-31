package com.org.covidcare.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Window
import com.org.covidcare.R
import com.org.covidcare.utilities.FIREBASE_NEWSID

/**
 * Created by ishwari s on 6/17/2020.
 */
class SplashActivity : AppCompatActivity() {

    private val splashTimeOut: Long = 2000 // 2 sec

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_splash)
        val notifyIntent = Intent(this, DashboardActivity::class.java)
        if (intent.extras != null) {
            notifyIntent.putExtra(
                getString(R.string.notification_intent_extra_name),
                getString(R.string.notification_intent_extra_value)
            )
            notifyIntent.putExtra(
                getString(R.string.notification_channel_id),
                intent.extras?.get(FIREBASE_NEWSID).toString()
            )
        }
        Handler().postDelayed({
            startActivity(notifyIntent)
            finish()
        }, splashTimeOut)

    }

}