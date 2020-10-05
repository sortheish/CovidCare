package com.org.covidcare.service

import android.view.View

/**
 * Created by Aishwarya on 05/10/2020.
 */

interface AdService {

    interface BannerAdPresenter {
        fun getBannerAd(view: View)
    }

    interface MrecAdPresenter {
        fun getMrecAd(view: View)
    }

    interface InterstitialAdPresenter {
        fun getInterstitialAd()
    }
}