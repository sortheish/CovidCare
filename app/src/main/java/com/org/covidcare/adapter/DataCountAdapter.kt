package com.org.covidcare.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.org.covidcare.R
import com.org.covidcare.model.Count
import com.squareup.picasso.Picasso


/**
 * Created by ishwari s on 6/23/2020.
 */
class DataCountAdapter(private val dataCount: ArrayList<Count>, val itemClick: (Count) -> Unit) :
    RecyclerView.Adapter<DataCountAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_of_state_countries, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataCount.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        dataCount.sortByDescending { it.cases_confirmed }
        holder.bindCountData(dataCount[position])
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

        fun bindCountData(dataCount: Count) {
            textStateCountryName.text = dataCount.region_name
            textConfirmedValue.text = dataCount.cases_confirmed.toString()
            textRecoveredValue.text = dataCount.case_recovered.toString()
            textDeceasedValue.text = dataCount.case_death.toString()
            if (dataCount.region_flag == "") {
                imageFlag.visibility = View.GONE
            } else {
                imageFlag.visibility = View.VISIBLE
                Picasso.with(itemView.context).load(dataCount.region_flag).into(imageFlag)
            }
            itemView.setOnClickListener { itemClick(dataCount) }
        }
    }
}