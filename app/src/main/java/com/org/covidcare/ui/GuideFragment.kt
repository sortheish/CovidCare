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
class GuideFragment : Fragment() {

    companion object {
        fun newInstance(): GuideFragment = GuideFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_guide, container,
            false
        )
    }
}