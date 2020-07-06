package com.org.covidcare.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.org.covidcare.R
import com.org.covidcare.model.District
import com.org.covidcare.utilities.CovidData

/**
 * Created by ishwari s on 6/24/2020.
 */
class DistrictAdapter(private val districts: ArrayList<District>) :
    RecyclerView.Adapter<DistrictAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_of_state_countries, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return districts.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        districts.sortByDescending { it.cases_confirmed  }
        holder.bindDistrictData(districts[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val textStateCountryName =
            itemView.findViewById<TextView>(R.id.text_state_countries_name)!!
        private val textConfirmedValue =
            itemView.findViewById<TextView>(R.id.list_confirmed_value)!!
        private val textRecoveredValue =
            itemView.findViewById<TextView>(R.id.list_recovered_value)!!
        private val textDeceasedValue = itemView.findViewById<TextView>(R.id.list_deceased_value)!!
        private val imageFlag = itemView.findViewById<ImageView>(R.id.imageViewFlag)

        fun bindDistrictData(district: District) {
            textStateCountryName.text = district.district_name
            textConfirmedValue.text = district.cases_confirmed.toString()
            textRecoveredValue.text = district.case_recovered.toString()
            textDeceasedValue.text = district.case_death.toString()
            imageFlag.visibility = View.GONE

        }
    }

}