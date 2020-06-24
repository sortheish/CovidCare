package com.org.covidcare.presenter

import android.view.View
import com.org.covidcare.service.CountService
import com.org.covidcare.service.CountriesModel
import com.org.covidcare.service.CountriesService

/**
 * Created by ishwari s on 6/19/2020.
 */
class CountriesServicePresenter(countryView: CountriesService.CountriesView) :
    CountriesService.CountriesPresenter,
    CountService.CountPresenter {
    private var view: CountriesService.CountriesView = countryView
    private var model = CountriesModel()
    override fun getCountries(complete: (Boolean) -> Unit) {
        model.getCountries(complete)
    }

    override fun setCountryData(view: View) {
        this.view.setCountryData(view)
    }

    override fun clearData() {
        model.clearData()
    }

    override fun getConfirmedCount(): Int = model.getConfirmedCount()

    override fun getRecoveredCount(): Int = model.getRecoveredCount()

    override fun getDeceasedCount(): Int = model.getDeceasedCount()
}