package com.org.covidcare.utilities

import android.os.Build
import androidx.annotation.RequiresApi
import com.org.covidcare.model.Count
import com.org.covidcare.model.Country
import com.org.covidcare.model.District
import com.org.covidcare.model.State
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by ishwari s on 6/22/2020.
 */
object CovidData {

    val dataCount = ArrayList<Count>()
    val countries = ArrayList<Country>()
    val states = ArrayList<State>()
    val districts = ArrayList<District>()
    fun compareToDay(userDate: String?): Boolean {
        if (userDate == null) {
            return false
        }
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val userDate = sdf.parse(userDate)
        val currentDate = sdf.parse((LocalDateTime.now()).toString())
        if(userDate?.compareTo(currentDate)==0){
            return true
        }
        return false
    }
}