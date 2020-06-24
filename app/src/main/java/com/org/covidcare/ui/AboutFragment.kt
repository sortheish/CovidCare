package com.org.covidcare.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.org.covidcare.R

/**
 * Created by ishwari s on 6/17/2020.
 */
class AboutFragment : Fragment() {
    companion object {
        fun newInstance(): AboutFragment = AboutFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_about, container,
            false
        )
    }
}