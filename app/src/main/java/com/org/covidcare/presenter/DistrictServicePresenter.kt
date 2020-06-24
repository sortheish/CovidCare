package com.org.covidcare.presenter

import com.org.covidcare.service.DistrictModel
import com.org.covidcare.service.DistrictService

/**
 * Created by ishwari s on 6/19/2020.
 */
class DistrictServicePresenter : DistrictService.DistrictPresenter {
    private var model = DistrictModel()

    override fun getDistricts(state_name: String, complete: (Boolean) -> Unit) {
        model.getDistricts(state_name, complete)
    }
}