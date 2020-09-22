package com.org.covidcare.ui

import android.content.Intent
import android.os.Bundle
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

/**
 * Created by ishwari s on 6/16/2020.
 */
class DashboardActivity : AppCompatActivity(), View.OnClickListener,
    NotificationInfoService.NotificationInfoView {
    private var notificationInfoPresenter: NotificationInfoPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        findViewById<ImageButton>(R.id.btnShareApp).setOnClickListener(this)
        notificationInfoPresenter = NotificationInfoPresenter(this)
        notificationInfoPresenter?.setNotificationDetailsData(fragment_container)
        bottom_navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
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

