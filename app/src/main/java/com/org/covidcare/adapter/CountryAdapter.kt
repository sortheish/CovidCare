package com.org.covidcare.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.org.covidcare.R
import com.org.covidcare.model.Count

/**
 * Created by ishwari s on 6/22/2020.
 */
class CountryAdapter(private val dataCount: ArrayList<Count>) :
    RecyclerView.Adapter<CountryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_of_state_countries, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataCount.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindMessage(dataCount[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val textStateCountryName =
            itemView.findViewById<TextView>(R.id.text_state_countries_name)!!
        private val textConfirmedValue =
            itemView.findViewById<TextView>(R.id.list_confirmed_value)!!
        private val textRecoveredValue =
            itemView.findViewById<TextView>(R.id.list_recovered_value)!!
        private val textDeceasedValue = itemView.findViewById<TextView>(R.id.list_deceased_value)!!

        fun bindMessage(dataCount: Count) {
            textStateCountryName.text = dataCount.region_name
            textConfirmedValue.text = dataCount.cases_confirmed.toString()
            textRecoveredValue.text = dataCount.case_recovered.toString()
            textDeceasedValue.text = dataCount.case_death.toString()
        }
    }

}