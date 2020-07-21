package com.org.covidcare.presenter

import android.view.View
import com.org.covidcare.service.CountService
import com.org.covidcare.service.CountriesModel
import com.org.covidcare.service.CountriesService
import com.org.covidcare.service.TodayCountService

/**
 * Created by ishwari s on 6/19/2020.
 */
class CountriesServicePresenter(countryView: CountriesService.CountriesView) :
    CountriesService.CountriesPresenter,
    CountService.CountPresenter, TodayCountService.TodayCountPresenter {
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

    override fun getActiveCount(): Int = model.getActiveCount()

    override fun getTodayConfirmedCount(): Int = model.getTodayConfirmedCount()

    override fun getTodayRecoveredCount(): Int = model.getTodayRecoveredCount()

    override fun getTodayDeceasedCount(): Int = model.getTodayDeceasedCount()
}