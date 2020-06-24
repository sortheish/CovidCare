package com.org.covidcare.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.org.covidcare.R
import com.org.covidcare.presenter.DistrictServicePresenter
import kotlinx.android.synthetic.main.activity_dashboard.*

/**
 * Created by ishwari s on 6/16/2020.
 */
class DashboardActivity : AppCompatActivity() {
    // private var countryPresenter: CountriesServicePresenter? = null
    private var districtPresenter: DistrictServicePresenter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        bottom_navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        openFragment(HomeFragment.newInstance())

        districtPresenter = DistrictServicePresenter()
        /*districtPresenter!!.getDistricts { districtSuccess->
            if(districtSuccess){
                Log.e("districtSuccess", "countrySuccess:$districtSuccess")
            }
        }*/
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