package com.org.covidcare.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.org.covidcare.R
import com.org.covidcare.adapter.DataCountAdapter
import com.org.covidcare.adapter.DistrictAdapter
import com.org.covidcare.model.Count
import com.org.covidcare.presenter.DistrictServicePresenter
import com.org.covidcare.service.DistrictService
import com.org.covidcare.utilities.CovidData
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * Created by ishwari s on 6/23/2020.
 */
class StateDetailFragment : Fragment(), DistrictService.DistrictView{
    private lateinit var objCount: Count
    private lateinit var textConfirmed: TextView
    private lateinit var textRecovered: TextView
    private lateinit var textDeceased: TextView
    private lateinit var textStateName:TextView
    private var districtPresenter: DistrictServicePresenter? = null

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
       val view =  inflater.inflate(R.layout.fragment_state_detail, container, false)
        init(view)
        districtPresenter?.setDistrictData(view)
        return view
    }

    private fun init(view:View) {
        textConfirmed = view.findViewById(R.id.text_confirmed_state)
        textRecovered = view.findViewById(R.id.text_recovered_state)
        textDeceased = view.findViewById(R.id.text_deceased_state)
        textStateName = view.findViewById(R.id.text_details_state_name)

        districtPresenter = DistrictServicePresenter(this)
        districtPresenter!!.getDistricts(objCount.region_name) { districtSuccess->
            if(districtSuccess){
              setUpAdapter()
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
        textConfirmed.text = objCount.cases_confirmed.toString()
        textRecovered.text = objCount.case_recovered.toString()
        textDeceased.text = objCount.case_death.toString()
    }

}