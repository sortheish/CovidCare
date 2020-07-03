package com.org.covidcare.view

import android.view.View
import com.org.covidcare.model.Count

/**
 * Created by ishwari s on 6/26/2020.
 */
interface GraphService {

    interface GraphView {
        fun getBarChart(view:View,graphFor:String)
        fun getPieChart(view:View,count:Count)
    }

    interface GraphPresenter {
        fun getBarChart(view:View,graphFor:String)
        fun getPieChart(view:View,count: Count)
    }
}