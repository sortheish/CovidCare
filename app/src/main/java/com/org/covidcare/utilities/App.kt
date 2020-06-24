package com.org.covidcare.utilities

import android.app.Application

/**
 * Created by ishwari s on 6/19/2020.
 */
class App: Application() {
    companion object{
        lateinit var prefs: SharedPrefs
    }

    override fun onCreate() {
        prefs = SharedPrefs(applicationContext)
        super.onCreate()

    }
}