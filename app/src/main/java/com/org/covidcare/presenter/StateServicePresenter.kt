package com.org.covidcare.presenter

import android.view.View
import com.org.covidcare.service.CountService
import com.org.covidcare.service.StateModel
import com.org.covidcare.service.StateService

/**
 * Created by ishwari s on 6/19/2020.
 */
class StateServicePresenter(stateView: StateService.StatesView) : StateService.StatePresenter,
    CountService.CountPresenter {
    private var view = stateView
    private var model = StateModel()

    override fun getStates(complete: (Boolean, String) -> Unit) {
        model.getStates(complete)
    }

    override fun getStateCount(state_name: String?,complete: (Boolean) -> Unit) {
        model.getStateCount(state_name,complete)
    }

    override fun setStateData(view: View) {
        this.view.setStateData(view)
    }

    override fun clearData() {
        model.clearData()
    }

    override fun getConfirmedCount(): Int = model.getConfirmedCount()

    override fun getRecoveredCount(): Int = model.getRecoveredCount()

    override fun getDeceasedCount(): Int = model.getDeceasedCount()

    override fun getActiveCount(): Int = model.getActiveCount()
}