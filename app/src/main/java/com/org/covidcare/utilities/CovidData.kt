package com.org.covidcare.utilities

import android.content.Context
import android.net.ConnectivityManager
import android.text.TextUtils
import android.util.Log
import com.org.covidcare.model.Count
import com.org.covidcare.model.District
import com.org.covidcare.model.NotificationInfo
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


/**
 * Created by ishwari s on 6/22/2020.
 */
object CovidData {

    val dataCount = ArrayList<Count>()
    val districts = ArrayList<District>()
    val graphDate = ArrayList<String>()
    val graphCount = ArrayList<Count>()
    val notifications = ArrayList<NotificationInfo>()

    fun logout() {
        App.prefs.userName = ""
        App.prefs.userMobileNumber = ""
        App.prefs.isLoggedIn = false
    }

    fun dateForGraph(graphDate: String): String {
        var outputDateStr = ""
        try {
            val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            val outputFormat: DateFormat = SimpleDateFormat("ddMMM", Locale.ENGLISH)
            val date = inputFormat.parse(graphDate)
            outputDateStr = outputFormat.format(date!!)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return outputDateStr
    }

    fun hasNetworkAvailable(context: Context): Boolean {
        val service = Context.CONNECTIVITY_SERVICE
        val manager = context.getSystemService(service) as ConnectivityManager?
        val network = manager?.activeNetwork
        return (network != null)
    }

    fun isValidPhone(phone: CharSequence): Boolean {
        return when {
            TextUtils.isEmpty(phone) -> {
                false
            }
            phone.length > 10 -> {
                return false
            }
            else -> {
                android.util.Patterns.PHONE.matcher(phone).matches()
            }
        }
    }

    fun getDate(timestamp: Long) :String {
        val inputFormat: DateFormat = SimpleDateFormat("ddMMM", Locale.US)
        return inputFormat.format(timestamp).toString()
    }

    fun getTime(timestamp: Long) :String {
        val inputFormat: DateFormat = SimpleDateFormat("hh:mmaa", Locale.US)
        Log.e("Value Date",""+ inputFormat.format(timestamp).toString())
        return inputFormat.format(timestamp).toString()
    }
}