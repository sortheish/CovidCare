package com.org.covidcare.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.org.covidcare.R
import com.org.covidcare.adapter.DataCountAdapter
import com.org.covidcare.model.Count
import com.org.covidcare.presenter.CountriesServicePresenter
import com.org.covidcare.presenter.StateServicePresenter
import com.org.covidcare.service.CountriesService
import com.org.covidcare.service.StateService
import com.org.covidcare.utilities.*
import com.org.covidcare.utilities.CovidData.dataCount
import com.org.covidcare.view.GraphViewPresenter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*


/**
 * Created by ishwari s on 6/17/2020.
 */
class HomeFragment : Fragment(), CountriesService.CountriesView,
    StateService.StatesView {
    private var countryPresenter: CountriesServicePresenter? = null
    private var graphPresenter: GraphViewPresenter? = null
    private var statePresenter: StateServicePresenter? = null
    private lateinit var textConfirmed: TextView
    private lateinit var textRecovered: TextView
    private lateinit var textDeceased: TextView
    private var dataCountAdapterAdapter: DataCountAdapter? = null

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
        countryPresenter!!.getCountries { countrySuccess ->
            if (countrySuccess) {
                val countIndia = dataCount.find { it.region_name == "India" }
                val textConfirmedIndia ="${countIndia?.cases_confirmed.toString()} (+${countIndia?.todayCases.toString()})"
                val textRecoveredIndia = "${countIndia?.case_recovered.toString()} (+${countIndia?.todayRecovered.toString()})"
                val textDeceasedIndia = "${countIndia?.case_death.toString()} (+${countIndia?.todayDeaths.toString()})"
                textConfirmed.text = textConfirmedIndia
                textRecovered.text = textRecoveredIndia
                textDeceased.text = textDeceasedIndia
            }
        }
        statePresenter!!.getStates { stateSuccess, _ ->
            if (stateSuccess) {
                setUpAdapter(dataCount)
                statePresenter?.setStateData(view)
            }
        }
        view.toggle_india_world.check(R.id.btnIndia)
        view.findViewById<Button>(R.id.btnIndia).isClickable = false
        view.toggle_india_world.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.btnIndia -> {
                        if (CovidData.hasNetworkAvailable(context!!)) {
                            countryPresenter!!.getCountries { countrySuccess ->
                                if (countrySuccess) {
                                    val countIndia = dataCount.find { it.region_name == "India" }
                                    val textConfirmedIndia ="${countIndia?.cases_confirmed.toString()} (+${countIndia?.todayCases.toString()})"
                                    val textRecoveredIndia = "${countIndia?.case_recovered.toString()} (+${countIndia?.todayRecovered.toString()})"
                                    val textDeceasedIndia = "${countIndia?.case_death.toString()} (+${countIndia?.todayDeaths.toString()})"
                                    textConfirmed.text = textConfirmedIndia
                                    textRecovered.text = textRecoveredIndia
                                    textDeceased.text = textDeceasedIndia
                                }
                            }
                            statePresenter!!.getStates { stateSuccess, _ ->
                                if (stateSuccess) {
                                    statePresenter?.setStateData(view)
                                }
                                dataCountAdapterAdapter?.notifyDataSetChanged()
                            }
                            view.findViewById<PieChart>(R.id.pieChart).visibility = View.GONE
                            view.findViewById<BarChart>(R.id.barChart).visibility = View.VISIBLE
                            view.findViewById<RadioGroup>(R.id.radioGroupDetail).visibility =
                                View.VISIBLE
                        } else {
                            dataCount.clear()
                            dataCountAdapterAdapter?.notifyDataSetChanged()
                            view.findViewById<TextView>(R.id.text_all_data).text = ""
                            view.findViewById<BarChart>(R.id.barChart).visibility = View.INVISIBLE
                            view.findViewById<PieChart>(R.id.pieChart).visibility = View.INVISIBLE
                            showAlert()
                        }
                        toggleButtonValue = INDIA
                        view.findViewById<Button>(R.id.btnWorld).isClickable = true
                        view.findViewById<Button>(R.id.btnIndia).isClickable = false
                    }
                    R.id.btnWorld -> {

                        if (CovidData.hasNetworkAvailable(context!!)) {
                            countryPresenter!!.getCountries { countrySuccess ->
                                if (countrySuccess) {
                                    countryPresenter?.setCountryData(view)
                                }
                                dataCountAdapterAdapter?.notifyDataSetChanged()
                            }
                            view.findViewById<RadioGroup>(R.id.radioGroupDetail).visibility =
                                View.GONE
                            view.findViewById<BarChart>(R.id.barChart).visibility = View.GONE
                            view.findViewById<PieChart>(R.id.pieChart).visibility = View.VISIBLE
                        } else {
                            dataCount.clear()
                            dataCountAdapterAdapter?.notifyDataSetChanged()
                            view.findViewById<TextView>(R.id.text_all_data).text = ""
                            view.findViewById<BarChart>(R.id.barChart).visibility = View.INVISIBLE
                            view.findViewById<PieChart>(R.id.pieChart).visibility = View.INVISIBLE
                            showAlert()
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
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

        graphPresenter = GraphViewPresenter()
    }

    override fun setCountryData(view: View) {
        val textConfirmedValue ="${countryPresenter?.getConfirmedCount().toString()} (+${countryPresenter?.getTodayConfirmedCount().toString()})"
        val textRecoveredValue ="${countryPresenter?.getRecoveredCount().toString()} (+${countryPresenter?.getTodayRecoveredCount().toString()})"
        val textDeceasedValue ="${countryPresenter?.getDeceasedCount().toString()} (+${countryPresenter?.getTodayDeceasedCount().toString()})"
        textConfirmed.text = textConfirmedValue
        textRecovered.text = textRecoveredValue
        textDeceased.text = textDeceasedValue

        graphPresenter?.getPieChart(
            view, Count(
                "World",
                "",
                countryPresenter!!.getConfirmedCount(),
                countryPresenter!!.getRecoveredCount(),
                countryPresenter!!.getDeceasedCount(),
                countryPresenter!!.getActiveCount(),
                countryPresenter!!.getTodayConfirmedCount(),
                countryPresenter!!.getTodayRecoveredCount(),
                countryPresenter!!.getTodayDeceasedCount()
            )
        )
        view.findViewById<TextView>(R.id.text_all_data).text =
            getString(R.string.text_all_countries)

    }

    override fun setStateData(view: View) {
        /*textConfirmed.text = statePresenter?.getConfirmedCount().toString()
        textRecovered.text = statePresenter?.getRecoveredCount().toString()
        textDeceased.text = statePresenter?.getDeceasedCount().toString()*/

        view.findViewById<RadioGroup>(R.id.radioGroupDetail).visibility = View.VISIBLE
        graphPresenter?.getBarChart(view, COUNTRY_CASES)
        radioGroupDetail.check(R.id.rad_btn_conf)
        view.radioGroupDetail.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rad_btn_conf -> {
                    graphPresenter?.getBarChart(view, COUNTRY_CASES)
                }
                R.id.rad_btn_active -> {
                    graphPresenter?.getBarChart(view, COUNTRY_ACTIVE)
                }
                R.id.rad_btn_recover -> {
                    graphPresenter?.getBarChart(view, COUNTRY_RECOVERED)
                }
                R.id.rad_btn_death -> {
                    graphPresenter?.getBarChart(view, COUNTRY_DEATHS)
                }
            }
        }
        view.findViewById<TextView>(R.id.text_all_data).text = getString(R.string.text_all_state)
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.fragment_container, fragment)
        transaction?.addToBackStack(null)
        transaction?.commit()
    }

    private fun showAlert() {
       MaterialAlertDialogBuilder(
            activity,
            R.style.AlertDialogCustom
        )
            .setTitle(getString(R.string.text_alert_internet))
            .setPositiveButton("Ok", /* listener = */ null)
            .show()
    }

}

