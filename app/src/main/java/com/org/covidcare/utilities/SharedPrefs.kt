package com.org.covidcare.utilities

import android.content.Context
import android.content.SharedPreferences
import com.android.volley.toolbox.Volley

/**
 * Created by ishwari s on 6/19/2020.
 */
class SharedPrefs(context:Context){

    private val prefs: SharedPreferences  = context.getSharedPreferences(PREFS_FILENAME,0)

    var isLoggedIn: Boolean
        get() = prefs.getBoolean(IS_LOGGED_IN,false)
        set(value) = prefs.edit().putBoolean(IS_LOGGED_IN,value).apply()

    var userName: String?
        get() = prefs.getString(USER_NAME, "")
        set(value) = prefs.edit().putString(USER_NAME,value).apply()

    var userMobileNumber: String?
        get() = prefs.getString(USER_NAME, "")
        set(value) = prefs.edit().putString(USER_MOBILE_NUMBER,value).apply()

    val requestQueue = Volley.newRequestQueue(context)!!
}
