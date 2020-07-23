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
import kotlinx.android.synthetic.main.activity_dashboard.*


/**
 * Created by ishwari s on 6/16/2020.
 */
class DashboardActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        findViewById<ImageButton>(R.id.btnShareApp).setOnClickListener(this)
        bottom_navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        openFragment(HomeFragment.newInstance())
    }

    private val mOnNavigationItemSelectedListener: BottomNavigationView.OnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item: MenuItem ->
            return@OnNavigationItemSelectedListener when (item.itemId) {
                R.id.page_home -> {
                    openFragment(HomeFragment.newInstance())
                    true
                }
                R.id.page_guide -> {
                    openFragment(GuideFragment.newInstance())
                    true
                }
                /*R.id.page_about -> {
                    openFragment(AboutFragment.newInstance())
                    true
                }*/
                else -> false
            }
        }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
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
}

