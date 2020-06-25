package com.org.covidcare.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.org.covidcare.R
import com.org.covidcare.adapter.DataCountAdapter
import com.org.covidcare.model.Count
import com.org.covidcare.presenter.CountriesServicePresenter
import com.org.covidcare.presenter.StateServicePresenter
import com.org.covidcare.service.CountriesService
import com.org.covidcare.service.StateService
import com.org.covidcare.utilities.CovidData
import com.org.covidcare.utilities.INDIA
import com.org.covidcare.utilities.WORLD
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
    private var toggleButtonValue: String? = INDIA

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

        statePresenter!!.getStates { stateSuccess, _ ->
            if (stateSuccess) {
                setUpAdapter(CovidData.dataCount)
                statePresenter?.setStateData(view)
            }
        }
        view.toggle_india_world.check(R.id.btnIndia)
        view.findViewById<Button>(R.id.btnIndia).isClickable = false
        view.toggle_india_world.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.btnIndia->{
                        statePresenter!!.getStates { stateSuccess, _ ->
                            if (stateSuccess) {
                                statePresenter?.setStateData(view)
                                dataCountAdapterAdapter.notifyDataSetChanged()
                            }
                        }
                        toggleButtonValue = INDIA
                        view.findViewById<Button>(R.id.btnWorld).isClickable = true
                        view.findViewById<Button>(R.id.btnIndia).isClickable = false
                    }
                    R.id.btnWorld->{
                        countryPresenter!!.getCountries { countrySuccess ->
                            if (countrySuccess) {
                                countryPresenter?.setCountryData(view)
                                dataCountAdapterAdapter.notifyDataSetChanged()
                            }
                        }
                        toggleButtonValue = WORLD
                        view.findViewById<Button>(R.id.btnWorld).isClickable = false
                        view.findViewById<Button>(R.id.btnIndia).isClickable = true
                    }
                }
            }
        }
        return view
    }

    private fun setUpAdapter(dataCount: ArrayList<Count>) {
        dataCountAdapterAdapter = DataCountAdapter(dataCount) { data_value ->
            if (toggleButtonValue.equals(INDIA)) {
                openFragment(StateDetailFragment.newInstance(data_value))
            }
        }
        val layoutManager = LinearLayoutManager(activity)
        list_of_data.layoutManager = layoutManager
        list_of_data.adapter = dataCountAdapterAdapter
    }

    private fun init(view: View) {
        textConfirmed = view.findViewById(R.id.text_confirmed_state)
        textRecovered = view.findViewById(R.id.text_recovered_state)
        textDeceased = view.findViewById(R.id.text_deceased_state)

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

    private fun openFragment(fragment: Fragment) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.fragment_container, fragment)
        transaction?.addToBackStack(null)
        transaction?.commit()
    }
}