package com.org.covidcare.presenter

import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.org.covidcare.R
import com.org.covidcare.service.AdService
import com.org.covidcare.ui.GuideFragment
import com.org.covidcare.ui.HomeFragment
import com.org.covidcare.utilities.BANNER_AD_ID
import com.org.covidcare.utilities.INTERSTITIAL_AD_ID
import com.org.covidcare.utilities.MREC_AD_ID
import com.vungle.warren.*
import com.vungle.warren.error.VungleException

/**
 * Created by Aishwarya on 05/10/2020.
 */

class AdPresenter: AdService.BannerAdPresenter,
                   AdService.MrecAdPresenter,
                   AdService.InterstitialAdPresenter {

    override fun getBannerAd(view: View) {
        val loadAdCallback = object : LoadAdCallback {

            override fun onAdLoad(id: String?) {
                if (id != null) {
                    addBannerAd(BANNER_AD_ID, view)
                }
            }

            override fun onError(id: String?, exception: VungleException?) {
                Log.e("Error", "Exception" + exception?.localizedMessage)
            }
        }
        Banners.loadBanner(BANNER_AD_ID, AdConfig.AdSize.BANNER, loadAdCallback)
    }

    fun addBannerAd(id: String, view: View) {
        if (Banners.canPlayAd(BANNER_AD_ID, AdConfig.AdSize.BANNER)) {
            val playAdCallback = object : PlayAdCallback {

                override fun onAdStart(id: String?) {
                    Log.d("onAdStart", "onAdStart")
                }

                override fun onError(id: String?, exception: VungleException?) {
                    Log.e("onError", "onError" + exception?.localizedMessage)
                }

                override fun onAdEnd(id: String?) {
                    view.findViewById<LinearLayout>(R.id.frame_banner).visibility = View.GONE
                }

                override fun onAdClick(id: String?) {
                    Log.d("onAdClick", "onAdClick")
                }

                override fun onAdRewarded(id: String?) {
                    Log.d("onAdRewarded", "onAdRewarded")
                }

                override fun onAdEnd(id: String?, completed: Boolean, isCTAClicked: Boolean) {
                    view.findViewById<LinearLayout>(R.id.frame_banner).visibility = View.GONE
                }

                override fun onAdLeftApplication(id: String?) {
                    Log.d("onAdLeftApplication", "onAdLeftApplication")
                }
            }
            GuideFragment.vungleBanner =
                Banners.getBanner(BANNER_AD_ID, AdConfig.AdSize.BANNER, playAdCallback)!!
            view.findViewById<LinearLayout>(R.id.frame_banner).addView(GuideFragment.vungleBanner)
        }
    }

    override fun getMrecAd(view: View) {
        val loadAdCallback = object : LoadAdCallback {
            override fun onAdLoad(id: String?) {
                Log.d("AdLoad", "AdLoad" + id)
                if (id != null) {
                    addMrecAd(MREC_AD_ID, view)
                }
            }

            override fun onError(id: String?, exception: VungleException?) {
                Log.e("onError", "onError" + exception?.localizedMessage)
            }
        }
        Vungle.loadAd(MREC_AD_ID, loadAdCallback)
    }

    fun addMrecAd(id: String, view: View) {
        val adConfig = AdConfig()
        adConfig.adSize = AdConfig.AdSize.VUNGLE_MREC
        adConfig.setMuted(true)

        val playAdCallback = object : PlayAdCallback {

            override fun onAdLeftApplication(id: String?) {
                Log.d("onAdLeftApplication", "onAdLeftApplication")
            }

            override fun onAdClick(id: String?) {
                HomeFragment.vungleNativeAd.finishDisplayingAd()
            }

            override fun onAdRewarded(id: String?) {
                Log.d("onAdRewarded", "onAdRewarded")
            }

            override fun onAdEnd(id: String?, completed: Boolean, isCTAClicked: Boolean) {
                view.findViewById<RelativeLayout>(R.id.layout_mrec_add).visibility = View.GONE
                Log.d("onAdEnd", "onAdEnd")
            }

            override fun onAdEnd(id: String?) {
                view.findViewById<RelativeLayout>(R.id.layout_mrec_add).visibility = View.GONE
                Log.d("onAdEnd", "onAdEnd")
            }

            override fun onAdStart(id: String?) {
                Log.d("onAdStart", "Adstart" + id)
            }

            override fun onError(id: String?, exception: VungleException?) {
                Log.e("onError", "onError" + exception?.localizedMessage)
            }
        }
        HomeFragment.vungleNativeAd =
            Vungle.getNativeAd(MREC_AD_ID, adConfig, playAdCallback)!!
        HomeFragment.nativeAdView = HomeFragment.vungleNativeAd!!.renderNativeView()
        view.findViewById<RelativeLayout>(R.id.layout_mrec_add).addView(HomeFragment.nativeAdView)
    }

    override fun getInterstitialAd() {
        val loadAdCallback = object : LoadAdCallback {
            override fun onAdLoad(id: String?) {
                addInterstitialAd(INTERSTITIAL_AD_ID)
            }

            override fun onError(id: String?, exception: VungleException?) {
                Log.e("onError", "Error :" + exception?.localizedMessage)
            }
        }
        Vungle.loadAd(INTERSTITIAL_AD_ID, loadAdCallback)
    }

    private fun addInterstitialAd(id: String?) {
        if (Vungle.canPlayAd(INTERSTITIAL_AD_ID)) {
            val playAdCallback = object : PlayAdCallback {
                override fun onAdLeftApplication(id: String?) {
                    Log.d("onAdLeftApplication", "onAdLeftApplication")
                }

                override fun onAdClick(id: String?) {
                    Log.d("onAdClick", "onAdClick")
                }

                override fun onAdRewarded(id: String?) {
                    Log.d("onAdRewarded", "onAdRewarded")
                }

                override fun onAdEnd(id: String?, completed: Boolean, isCTAClicked: Boolean) {
                    Log.d("onAdEnd", "onAdEnd")
                }

                override fun onAdEnd(id: String?) {
                    Log.d("onAdEnd", "onAdEnd")
                }

                override fun onAdStart(id: String?) {
                    Log.d("onAdStart", "onAdStart")
                }

                override fun onError(id: String?, exception: VungleException?) {
                    Log.e("onError", "onError" + exception?.localizedMessage)
                }
            }
            Vungle.playAd(INTERSTITIAL_AD_ID, null, playAdCallback)
        }
    }
}