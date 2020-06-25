package com.org.covidcare.service

import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.org.covidcare.model.District
import com.org.covidcare.utilities.*
import org.json.JSONException


/**
 * Created by ishwari s on 6/19/2020.
 */
class DistrictModel : DistrictService.DistrictModel {

    override fun getDistricts(state_name: String?, complete: (Boolean) -> Unit) {
        val getDistrict = object :
            JsonObjectRequest(Method.GET, DISTRICT_URL, null, Response.Listener { response ->
                clearData()
                try {
                    val stateData = response.getJSONObject(state_name!!)
                    val sData = stateData.getJSONObject(DISTRICT_DATA)
                    val keys = sData.keys()

                    for (x in 0 until sData.length()) {
                        val key = keys.next()
                        val district = sData.getJSONObject(key)
                        val cases = district.getString(DISTRICT_CASES)
                        val recovered = district.getString(DISTRICT_RECOVERED)
                        val deaths = district.getString(DISTRICT_DEATHS)
                        val newDistrict = District(key, cases, recovered, deaths)
                        CovidData.districts.add(newDistrict)
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
        App.prefs.requestQueue.add(getDistrict)
    }

    override fun clearData() {
        CovidData.districts.clear()
    }

}