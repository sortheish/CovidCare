package com.org.covidcare.service

import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.org.covidcare.model.Count
import com.org.covidcare.model.State
import com.org.covidcare.utilities.App
import com.org.covidcare.utilities.CovidData
import com.org.covidcare.utilities.STATE_URL
import org.json.JSONException
import kotlin.collections.ArrayList

/**
 * Created by ishwari s on 6/19/2020.
 */
class StateModel : StateService.StateModel, CountService.CountModel {

    override fun getStates(complete: (Boolean, String) -> Unit) {
        val getState =
            object :
                JsonObjectRequest(Method.GET, STATE_URL, null, Response.Listener { response ->
                    clearData()
                    try {
                        val dataValue = response.getJSONArray("data")
                        for (x in 0 until dataValue.length()) {
                            val dayWiseUpdates = dataValue.getJSONObject(x)
                            val dayOfUpdates = dayWiseUpdates.getString("day")
                            if (CovidData.compareToDay(dayOfUpdates)) {
                                val regionalData = dayWiseUpdates.getJSONArray("regional")
                                for (x in 0 until regionalData.length()) {
                                    val stateData = regionalData.getJSONObject(x)
                                    val stateName: String = stateData.getString("loc")
                                    val cases = stateData.getInt("totalConfirmed")
                                    val recovered = stateData.getInt("discharged")
                                    val deaths = stateData.getInt("deaths")
                                    val newState = Count(stateName, cases, recovered, deaths)
                                    CovidData.dataCount.add(newState)
                                }
                            }
                        }
                        complete(true, "State")
                    } catch (e: JSONException) {
                        Log.e("JSON", "EXC:" + e.localizedMessage)
                        complete(false, "State")
                    }
                }, Response.ErrorListener { error ->
                    Log.e("ERROR", "Could not retrieve channels: $error")
                    complete(false, "State")
                }) {
                override fun getBodyContentType(): String {
                    return "application/json; charset=utf-8"
                }
            }
        App.prefs.requestQueue.add(getState)
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

}