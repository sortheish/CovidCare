package com.org.covidcare.service

/**
 * Created by ishwari s on 6/19/2020.
 */
interface DistrictService {
    interface DistrictModel {
        fun getDistricts(state_name: String, complete: (Boolean) -> Unit)
    }

    interface DistrictPresenter {
        fun getDistricts(state_name: String, complete: (Boolean) -> Unit)
    }
}