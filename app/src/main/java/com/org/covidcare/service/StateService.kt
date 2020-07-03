package com.org.covidcare.service

import android.view.View

/**
 * Created by ishwari s on 6/19/2020.
 */
interface StateService {
    interface StateModel {
        fun getStates(complete: (Boolean, String) -> Unit)
        fun getStateCount(state_name: String?,complete: (Boolean) -> Unit)
        fun clearData()
    }

    interface StatesView {
        fun setStateData(view: View)
    }

    interface StatePresenter {
        fun getStates(complete: (Boolean, String) -> Unit)
        fun getStateCount(state_name: String?,complete: (Boolean) -> Unit)
        fun setStateData(view: View)
        fun clearData()
    }
}