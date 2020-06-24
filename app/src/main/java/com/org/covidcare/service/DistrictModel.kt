package com.org.covidcare.service

import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.org.covidcare.model.District
import com.org.covidcare.model.State
import com.org.covidcare.utilities.App
import com.org.covidcare.utilities.CovidData
import com.org.covidcare.utilities.DISTRICT_URL
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by ishwari s on 6/19/2020.
 */
class DistrictModel : DistrictService.DistrictModel {

    val districts = ArrayList<District>()

    override fun getDistricts(state_name: String, complete: (Boolean) -> Unit) {
        val getDistrict = object :
            JsonObjectRequest(Method.GET, DISTRICT_URL, null, Response.Listener { response ->
                try {
                    val stateData = response.getJSONObject(state_name)
                    val sData = stateData.getJSONObject("districtData")
                    val keys = sData.keys()
                    for (x in 0 until sData.length()) {
                        val key = keys.next()
                        val district = sData.getJSONObject(key)
                        val cases = district.getString("confirmed")
                        val recovered = district.getString("recovered")
                        val deaths = district.getString("deceased")
                        val newDistrict = District(key, cases, recovered, deaths)
                        CovidData.districts.add(newDistrict)

                        Log.e(
                            "ERROR",
                            "value:" + districts[x].district_name + districts[x].cases_confirmed + districts[x].case_recovered
                        )
                    }
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

}