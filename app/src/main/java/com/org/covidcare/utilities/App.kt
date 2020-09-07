package com.org.covidcare.utilities

import android.app.Application
import android.content.Context
import android.telephony.TelephonyManager
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import java.util.*


/**
 * Created by ishwari s on 6/19/2020.
 */
class App: Application() {
    companion object{
        lateinit var prefs: SharedPrefs
    }
    override fun onCreate() {
        prefs = SharedPrefs(applicationContext)
        super.onCreate()// setToken()
    }
}