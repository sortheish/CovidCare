package com.org.covidcare.service

import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.org.covidcare.model.Count
import com.org.covidcare.utilities.*
import org.json.JSONException


/**
 * Created by ishwari s on 6/19/2020.
 */
class CountriesModel : CountriesService.CountriesModel, CountService.CountModel, TodayCountService.TodayCountModel {

    override fun getCountries(complete: (Boolean) -> Unit) {
        val getCountries = object :
            JsonArrayRequest(Method.GET, COUNTRIES_URL, null, Response.Listener { response ->
                clearData()
                try {
                    for (x in 0 until response.length()) {
                        val countries = response.getJSONObject(x)
                        val countryFlag = countries.getJSONObject(COUNTRY_INFO)
                        val country = countries.getString(COUNTRY_NAME)
                        val cases: Int = countries.getInt(COUNTRY_CASES)
                        val recovered = countries.getInt(COUNTRY_RECOVERED)
                        val deaths = countries.getInt(COUNTRY_DEATHS)
                        val active = countries.getInt(COUNTRY_ACTIVE)
                        val casesToday: Int = countries.getInt(COUNTRY_TODAY_CASES)
                        val recoveredToday = countries.getInt(COUNTRY_TODAY_RECOVERED)
                        val deathsToday = countries.getInt(COUNTRY_TODAY_DEATHS)
                        val flag = countryFlag.getString(COUNTRY_flag)
                        val newCountry = Count(country, flag, cases, recovered, deaths, active,casesToday,recoveredToday,deathsToday)
                        CovidData.dataCount.add(newCountry)
                    }
                    complete(true)
                } catch (e: JSONException) {
                    Log.e("JSON", "EXC:" + e.localizedMessage)
                    complete(false)
                }
            }, Response.ErrorListener { error ->
                Log.e("ERROR", "Could not retrieve channels: $error")
                complete(false)
            }) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }
        }
        App.prefs.requestQueue.add(getCountries)
    }

    override fun clearData() {
        CovidData.dataCount.clear()
    }

    override fun getConfirmedCount(): Int {
        var sum = 0
        for (x in 0 until CovidData.dataCount.size) {
            sum += CovidData.dataCount[x].cases_confirmed
        }
        return sum
    }

    override fun getRecoveredCount(): Int {
        var sum = 0
        for (x in 0 until CovidData.dataCount.size) {
            sum += CovidData.dataCount[x].case_recovered
        }
        return sum
    }

    override fun getDeceasedCount(): Int {
        var sum = 0
        for (x in 0 until CovidData.dataCount.size) {
            sum += CovidData.dataCount[x].case_death
        }
        return sum
    }

    override fun getActiveCount(): Int {
        var sum = 0
        for (x in 0 until CovidData.dataCount.size) {
            sum += CovidData.dataCount[x].case_active
        }
        return sum
    }

    override fun getTodayConfirmedCount(): Int {
        var sum = 0
        for (x in 0 until CovidData.dataCount.size) {
            sum += CovidData.dataCount[x].todayCases
        }
        return sum
    }

    override fun getTodayRecoveredCount(): Int {
        var sum = 0
        for (x in 0 until CovidData.dataCount.size) {
            sum += CovidData.dataCount[x].todayRecovered
        }
        return sum
    }

    override fun getTodayDeceasedCount(): Int {
        var sum = 0
        for (x in 0 until CovidData.dataCount.size) {
            sum += CovidData.dataCount[x].todayDeaths
        }
        return sum
    }

}


