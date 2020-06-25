package com.org.covidcare.service

import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.org.covidcare.model.Count
import com.org.covidcare.utilities.*
import org.json.JSONException


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
                        val dataValue = response.getJSONArray(STATE_DATA)
                            val dayWiseUpdates = dataValue.getJSONObject(dataValue.length()-1)
                            val regionalData = dayWiseUpdates.getJSONArray(STATE_REGIONAL_DATA)
                                for (y in 0 until regionalData.length()) {
                                    val stateData = regionalData.getJSONObject(y)
                                    val stateName: String = stateData.getString(STATE_NAME)
                                    val cases = stateData.getInt(STATE_CASES)
                                    val recovered = stateData.getInt(STATE_RECOVERED)
                                    val deaths = stateData.getInt(STATE_DEATHS)
                                    val newState = Count(stateName, cases, recovered, deaths)
                                    CovidData.dataCount.add(newState)
                                }
                        complete(true, INDIA)
                    } catch (e: JSONException) {
                        Log.e("JSON", "EXC:" + e.localizedMessage)
                        complete(false, INDIA)
                    }
                }, Response.ErrorListener { error ->
                    Log.e("ERROR", "Could not retrieve channels: $error")
                    complete(false, INDIA)
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