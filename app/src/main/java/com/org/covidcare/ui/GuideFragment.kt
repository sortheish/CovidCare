package com.org.covidcare.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.org.covidcare.R
import com.org.covidcare.presenter.AdPresenter
import com.vungle.warren.*

/**
 * Created by ishwari s on 6/17/2020.
 */

class GuideFragment : Fragment() {
    private lateinit var adPresenter: AdPresenter

    companion object {
        fun newInstance(): GuideFragment = GuideFragment()
        lateinit var vungleBanner: VungleBanner
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_guide, container, false)
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adPresenter = AdPresenter()
        adPresenter.getBannerAd(view)
    }
}