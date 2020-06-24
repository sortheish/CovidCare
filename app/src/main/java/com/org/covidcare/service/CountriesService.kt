package com.org.covidcare.service

import android.view.View

/**
 * Created by ishwari s on 6/19/2020.
 */
interface CountriesService {
    interface CountriesModel {
        fun getCountries(complete: (Boolean) -> Unit)
        fun clearData()
    }

    interface CountriesView {
        fun setCountryData(view: View)
    }

    interface CountriesPresenter {
        fun getCountries(complete: (Boolean) -> Unit)
        fun setCountryData(view: View)
        fun clearData()
    }
}