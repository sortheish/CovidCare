package com.org.covidcare.presenter

import android.view.View
import com.org.covidcare.service.DistrictModel
import com.org.covidcare.service.DistrictService

/**
 * Created by ishwari s on 6/19/2020.
 */
class DistrictServicePresenter(districtView: DistrictService.DistrictView) :
    DistrictService.DistrictPresenter {
    private var model = DistrictModel()
    private var view = districtView

    override fun getDistricts(state_name: String?, complete: (Boolean) -> Unit) {
        model.getDistricts(state_name, complete)
    }

    override fun setDistrictData(view: View) {
        this.view.setDistrictData(view)
    }

    override fun clearData() {
        model.clearData()
    }
}