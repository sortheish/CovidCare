package com.org.covidcare.view

import android.view.View
import com.org.covidcare.model.Count

/**
 * Created by ishwari s on 6/26/2020.
 */
class GraphViewPresenter:GraphService.GraphPresenter {
    private var graphView = GraphView()
    override fun getBarChart(view: View,graphFor:String) {
        this.graphView.getBarChart(view,graphFor)
    }

    override fun getPieChart(view: View,count: Count) {
        this.graphView.getPieChart(view,count)
    }
}