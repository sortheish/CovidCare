package com.org.covidcare.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.org.covidcare.R
import com.org.covidcare.model.State

/**
 * Created by ishwari s on 6/23/2020.
 */
class StateAdapter(private val states: ArrayList<State>) :
    RecyclerView.Adapter<StateAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_of_state_countries, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return states.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindMessage(states[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val textStateCountryName =
            itemView.findViewById<TextView>(R.id.text_state_countries_name)!!
        private val textConfirmedValue =
            itemView.findViewById<TextView>(R.id.list_confirmed_value)!!
        private val textRecoveredValue =
            itemView.findViewById<TextView>(R.id.list_recovered_value)!!
        private val textDeceasedValue = itemView.findViewById<TextView>(R.id.list_deceased_value)!!

        fun bindMessage(state: State) {
            textStateCountryName.text = state.state_name
            textConfirmedValue.text = state.cases_confirmed.toString()
            textRecoveredValue.text = state.case_recovered.toString()
            textDeceasedValue.text = state.case_death.toString()
        }
    }

}