package com.org.covidcare.utilities

import android.content.Context
import android.net.ConnectivityManager
import com.org.covidcare.model.Count
import com.org.covidcare.model.District
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

    /*fun compareToDay(userDate: String?): Boolean {
        if (userDate == null) {
            return false
        }
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val dataDate = sdf.parse(userDate)
        val currentDate = sdf.parse((LocalDateTime.now()).toString())
        if(dataDate?.compareTo(currentDate)==0){
            return true
        }
        return false
    }*/

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
}