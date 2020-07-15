package com.org.covidcare.presenter

import com.org.covidcare.service.FirebaseModel
import com.org.covidcare.service.FirebaseService

/**
 * Created by ishwari s on 7/14/2020.
 */
class FirebaseServicePresenter: FirebaseService.FirebasePresenter {
    var model = FirebaseModel()

    override fun emailVerification(email:String) {
        model.emailVerification(email)
    }
}