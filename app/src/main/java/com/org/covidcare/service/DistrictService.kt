package com.org.covidcare.service

import android.view.View

/**
 * Created by ishwari s on 6/19/2020.
 */
interface DistrictService {
    interface DistrictModel {
        fun getDistricts(state_name: String?, complete: (Boolean) -> Unit)
        fun clearData()
    }

    interface DistrictView{
        fun setDistrictData(view:View)
    }

    interface DistrictPresenter {
        fun getDistricts(state_name: String?, complete: (Boolean) -> Unit)
        fun setDistrictData(view: View)
        fun clearData()
    }
}