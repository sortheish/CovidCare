package com.org.covidcare.service

/**
 * Created by ishwari s on 7/14/2020.
 */
interface FirebaseService {
    interface FirebaseModel{
        fun emailVerification(email:String)
    }

    interface FirebasePresenter{
        fun emailVerification(email:String)
    }
}