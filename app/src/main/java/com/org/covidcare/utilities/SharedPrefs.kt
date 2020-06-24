package com.org.covidcare.utilities

import android.content.Context
import android.content.SharedPreferences
import com.android.volley.toolbox.Volley

/**
 * Created by ishwari s on 6/19/2020.
 */
class SharedPrefs(context:Context){

    private val prefs: SharedPreferences?  = context.getSharedPreferences(PREFS_FILENAME,0)
    val requestQueue = Volley.newRequestQueue(context)!!
}
