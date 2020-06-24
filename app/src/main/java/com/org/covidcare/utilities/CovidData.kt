package com.org.covidcare.utilities

import com.org.covidcare.model.Count
import com.org.covidcare.model.District
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by ishwari s on 6/22/2020.
 */
object CovidData {

    val dataCount = ArrayList<Count>()
    val districts = ArrayList<District>()
    fun compareToDay(userDate: String?): Boolean {
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
    }
}