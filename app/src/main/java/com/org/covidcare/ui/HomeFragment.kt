package com.org.covidcare.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.button.MaterialButton
import com.org.covidcare.R
import com.org.covidcare.adapter.DataCountAdapter
import com.org.covidcare.model.Count
import com.org.covidcare.presenter.CountriesServicePresenter
import com.org.covidcare.presenter.StateServicePresenter
import com.org.covidcare.service.CountriesService
import com.org.covidcare.service.StateService
import com.org.covidcare.utilities.CovidData
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlin.collections.ArrayList

/**
 * Created by ishwari s on 6/17/2020.
 */
class HomeFragment : Fragment(), CountriesService.CountriesView,
    StateService.StatesView {
    private var countryPresenter: CountriesServicePresenter? = null
    private var statePresenter: StateServicePresenter? = null
    private lateinit var textConfirmed: TextView
    private lateinit var textRecovered: TextView
    private lateinit var textDeceased: TextView
    private lateinit var dataCountAdapterAdapter: DataCountAdapter
    private var toggleButtonValue: String? = null

    companion object {
        fun newInstance(): HomeFragment = HomeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(
            R.layout.fragment_home, container,
            false
        )
        init(view)

        statePresenter!!.getStates { stateSuccess, value_state ->
            if (stateSuccess) {
                setUpAdapter(CovidData.dataCount, value_state)
                statePresenter?.setStateData(view)
            }
        }
        view.toggle_india_world.addOnButtonCheckedListener { group, _, isChecked ->
            val checkedButton: MaterialButton? = group.findViewById(group.checkedButtonId)
            if (!isChecked) {
                toggleButtonValue = checkedButton?.text as String?
                if (toggleButtonValue.equals("World")) {
                    countryPresenter!!.getCountries { countrySuccess ->
                        if (countrySuccess) {
                            countryPresenter?.setCountryData(view)
                            dataCountAdapterAdapter.notifyDataSetChanged()
                        }
                    }
                } else {
                    statePresenter!!.getStates { stateSuccess, _ ->
                        if (stateSuccess) {
                            statePresenter?.setStateData(view)
                            dataCountAdapterAdapter.notifyDataSetChanged()
                        }
                    }
                }
            }
        }
        return view
    }

    private fun setUpAdapter(dataCount: ArrayList<Count>, value_state: String) {
        dataCountAdapterAdapter = DataCountAdapter(dataCount, value_state) {
            Toast.makeText(context, "State", Toast.LENGTH_LONG).show()
        }
        val layoutManager = LinearLayoutManager(activity)
        list_of_districts.layoutManager = layoutManager
        list_of_districts.adapter = dataCountAdapterAdapter
    }

    private fun init(view: View) {
        textConfirmed = view.findViewById(R.id.text_value_confirmed)
        textRecovered = view.findViewById(R.id.text_value_recovered_state)
        textDeceased = view.findViewById(R.id.text_value_deceased_state)

        countryPresenter = CountriesServicePresenter(this)
        statePresenter = StateServicePresenter(this)
    }

    override fun setCountryData(view: View) {
        textConfirmed.text = countryPresenter?.getConfirmedCount().toString()
        textRecovered.text = countryPresenter?.getRecoveredCount().toString()
        textDeceased.text = countryPresenter?.getDeceasedCount().toString()
        view.findViewById<TextView>(R.id.text_all_data).text =
            getString(R.string.text_all_countries)
    }

    override fun setStateData(view: View) {
        textConfirmed.text = statePresenter?.getConfirmedCount().toString()
        textRecovered.text = statePresenter?.getRecoveredCount().toString()
        textDeceased.text = statePresenter?.getDeceasedCount().toString()
        view.findViewById<TextView>(R.id.text_all_data).text = getString(R.string.text_all_state)
    }

}