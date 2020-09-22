package com.org.covidcare.view

import android.view.View

/**
 * Created by ishwari s on 7/9/2020.
 */
interface AboutService {
    interface AboutView {
        fun setDialog(view: View)
    }

    interface AboutPresenter {
        fun setDialog(view: View)
    }
}