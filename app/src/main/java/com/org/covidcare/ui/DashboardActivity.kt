package com.org.covidcare.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.org.covidcare.R
import kotlinx.android.synthetic.main.activity_dashboard.*

/**
 * Created by ishwari s on 6/16/2020.
 */
class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
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
                R.id.page_about -> {
                    openFragment(AboutFragment.newInstance())
                    true
                }
                else -> false
            }
        }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}