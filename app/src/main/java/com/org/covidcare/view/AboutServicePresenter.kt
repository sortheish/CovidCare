package com.org.covidcare.view

import android.app.Activity
import android.view.View
import com.org.covidcare.service.CountriesService

/**
 * Created by ishwari s on 7/9/2020.
 */
class AboutServicePresenter(aboutView: AboutService.AboutView):AboutService.AboutPresenter {
    private var view: AboutService.AboutView = aboutView

    override fun setDialog(view: View) {
        this.view.setDialog(view)
    }
}