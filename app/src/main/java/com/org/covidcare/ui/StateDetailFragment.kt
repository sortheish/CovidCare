package com.org.covidcare.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.org.covidcare.R
import com.org.covidcare.adapter.DistrictAdapter
import com.org.covidcare.model.Count
import com.org.covidcare.presenter.DistrictServicePresenter
import com.org.covidcare.presenter.StateServicePresenter
import com.org.covidcare.service.DistrictService
import com.org.covidcare.service.StateService
import com.org.covidcare.utilities.*
import com.org.covidcare.view.GraphViewPresenter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*

/**
 * Created by ishwari s on 6/23/2020.
 */
class StateDetailFragment : Fragment(), DistrictService.DistrictView, StateService.StatesView {
    private lateinit var objCount: Count
    private lateinit var textStateName: TextView
    private var districtPresenter: DistrictServicePresenter? = null
    private var statePresenter: StateServicePresenter? = null
    private var graphPresenter: GraphViewPresenter? = null

    companion object {
        private const val ARG_PARAM = "countObject"
        fun newInstance(count: Count) =
            StateDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PARAM, count)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            objCount = it.getParcelable(ARG_PARAM)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_state_detail, container, false)
        init(view)
        districtPresenter?.setDistrictData(view)
        return view
    }

    private fun init(view: View) {
        textStateName = view.findViewById(R.id.text_details_state_name)

        graphPresenter = GraphViewPresenter()
        districtPresenter = DistrictServicePresenter(this)
        statePresenter = StateServicePresenter(this)
        districtPresenter!!.getDistricts(objCount.region_name) { districtSuccess ->
            if (districtSuccess) {
                setUpAdapter()
            }
        }

        statePresenter!!.getStateCount(objCount.region_name) { stateSuccess ->
            if (stateSuccess) {
                graphPresenter?.getBarChart(view, COUNTRY_CASES)
                statePresenter!!.setStateData(view)
            }
        }
    }

    private fun setUpAdapter() {
        val layoutManager = LinearLayoutManager(activity)
        list_of_data.layoutManager = layoutManager
        list_of_data.adapter = DistrictAdapter(CovidData.districts)
    }

    override fun setDistrictData(view: View) {
        textStateName.text = objCount.region_name
        graphPresenter?.getPieChart(view, objCount)
    }

    override fun setStateData(view: View) {
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
    }

}