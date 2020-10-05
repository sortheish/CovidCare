package com.org.covidcare.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.org.covidcare.R
import com.org.covidcare.presenter.NotificationInfoPresenter
import com.org.covidcare.service.NotificationInfoService
import com.org.covidcare.utilities.App
import kotlinx.android.synthetic.main.activity_dashboard.*

import com.vungle.warren.Vungle;
import com.vungle.warren.AdConfig;              // Custom ad configurations
import com.vungle.warren.InitCallback;          // Initialization callback
import com.vungle.warren.LoadAdCallback;        // Load ad callback
import com.vungle.warren.PlayAdCallback;        // Play ad callback
import com.vungle.warren.VungleNativeAd;        // MREC ad
import com.vungle.warren.Banners;               // Banner ad
import com.vungle.warren.VungleBanner;          // Banner ad
import com.vungle.warren.Vungle.Consent;        // GDPR consent
import com.vungle.warren.VungleSettings         // Minimum disk space
import com.vungle.warren.error.VungleException  // onError message

/**
 * Created by ishwari s on 6/16/2020.
 */
class DashboardActivity : AppCompatActivity(), View.OnClickListener,
    NotificationInfoService.NotificationInfoView {
    private var notificationInfoPresenter: NotificationInfoPresenter? = null
    private val appId: String = "5bf49746b1fd5362ddda51e2"

    companion object {
        var count: Int = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        initVungleSdk()
        findViewById<ImageButton>(R.id.btnShareApp).setOnClickListener(this)
        notificationInfoPresenter = NotificationInfoPresenter(this)
        notificationInfoPresenter?.setNotificationDetailsData(fragment_container)
        bottom_navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private fun initVungleSdk() {
        val callback = object: InitCallback {
            override fun onSuccess() {
               Log.d("onSuccess", "onSuccess")
            }

            override fun onError(exception: VungleException?) {
                Log.d("onError", "onError" + exception?.localizedMessage)
            }

            override fun onAutoCacheAdAvailable(placementId: String?) {
                Log.d("onAutoCacheAvailable", "onAutoCacheAvailable")
            }
        }
        Vungle.init(appId, applicationContext, callback)
    }

    private val mOnNavigationItemSelectedListener: BottomNavigationView.OnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item: MenuItem ->
            return@OnNavigationItemSelectedListener when (item.itemId) {
                R.id.page_about -> {
                   // openFragment(WelcomeFragment())
                     if (App.prefs.isLoggedIn) {
                         val name: String? = App.prefs.userName
                         openFragment(WelcomeFragment.newInstance(name))
                     } else {
                         openFragment(AboutFragment.newInstance())
                     }
                    true
                }
                R.id.page_guide -> {
                    openFragment(GuideFragment.newInstance())
                    true
                }
                R.id.page_home -> {
                    openFragment(HomeFragment.newInstance())
                    true
                }
                else -> false
            }
        }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        //transaction.add(R.id.fragment_container, fragment, "tag1")
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnShareApp -> {
                val sharingIntent = Intent(Intent.ACTION_SEND)
                sharingIntent.type = "text/plain"
                sharingIntent.putExtra(
                    Intent.EXTRA_SUBJECT,
                    "APP NAME (Open it in Google Play Store to Download the Application)"
                )
                sharingIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.text_share_message))
                startActivity(Intent.createChooser(sharingIntent, "Share via"))
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        /*val homeItem: MenuItem = bottom_navigation.menu.getItem(0)
        bottom_navigation.selectedItemId = homeItem.itemId*/

    }

    override fun setNotificationDetailsData(view: View) {
        val menuFragment = intent.getStringExtra(getString(R.string.notification_intent_extra_name))
        if (menuFragment != null) {
            if (menuFragment == getString(R.string.notification_intent_extra_value)) {
                notificationInfoPresenter?.getDataFromServer(intent.getStringExtra(getString(R.string.notification_channel_id))) { notificationInfoData ->
                    openFragment(NotificationDetailsFragment.newInstance(notificationInfoData))
                }
           }
        } else {
           // openFragment(WelcomeFragment())
             if (App.prefs.isLoggedIn) {
                 val name: String? = App.prefs.userName
                 openFragment(WelcomeFragment.newInstance(name))
             } else {
                 openFragment(AboutFragment.newInstance())
             }
        }
    }
}

