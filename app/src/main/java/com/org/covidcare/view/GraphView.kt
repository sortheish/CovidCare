package com.org.covidcare.view

import android.graphics.Color
import android.view.View
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.PercentFormatter
import com.org.covidcare.R
import com.org.covidcare.model.Count
import com.org.covidcare.utilities.*
import kotlinx.android.synthetic.main.fragment_state_detail.view.pieChart

/**
 * Created by ishwari s on 6/26/2020.
 */
class GraphView:GraphService.GraphView {

    override fun getBarChart(view: View, graphFor:String) {
        val barChart =view.findViewById<BarChart>(R.id.barChart)
        var barDataSet = BarDataSet(emptyList(),"")
        val yValueGroup = ArrayList<BarEntry>()

        when (graphFor) {
            COUNTRY_CASES -> {
                for(x in 0 until CovidData.graphCount.size){
                    yValueGroup.add(BarEntry(x.toFloat(), CovidData.graphCount[x].cases_confirmed.toFloat()))
                    barDataSet = BarDataSet(yValueGroup, "")
                    barDataSet.setColors(Color.rgb(255,152,0))
                }
            }
            COUNTRY_ACTIVE->{
                for(x in 0 until CovidData.graphCount.size){
                    yValueGroup.add(BarEntry(x.toFloat(), CovidData.graphCount[x].case_active.toFloat()))
                    barDataSet = BarDataSet(yValueGroup, "")
                    barDataSet.setColors(Color.rgb(235,87,87))
                }
            }
            COUNTRY_RECOVERED -> {
                for(x in 0 until CovidData.graphCount.size){
                    yValueGroup.add(BarEntry(x.toFloat(), CovidData.graphCount[x].case_recovered.toFloat()))
                    barDataSet = BarDataSet(yValueGroup, "")
                    barDataSet.setColors(Color.rgb(39,174,69))
                }
            }
            COUNTRY_DEATHS -> {
                for(x in 0 until CovidData.graphCount.size){
                    yValueGroup.add(BarEntry(x.toFloat(), CovidData.graphCount[x].case_death.toFloat()))
                    barDataSet = BarDataSet(yValueGroup,"")
                    barDataSet.setColors(Color.rgb(16,16,16))
                }
            }
        }

        barDataSet.valueTextColor = view.context.getColor(R.color.app_text_color)
        barChart.description.isEnabled = false
        barChart.description.textSize = 0f
        barChart.data = BarData(barDataSet)
        barChart.barData.barWidth = 0.5F
        barChart.xAxis.textColor = view.context.getColor(R.color.app_text_color)
        barChart.axisLeft.textColor = view.context.getColor(R.color.app_text_color)
        barChart.axisRight.isEnabled = false
        barChart.xAxis.textSize = 10f
        barChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        barChart.xAxis.valueFormatter = IndexAxisValueFormatter(CovidData.graphDate)
        barChart.invalidate()
        barChart.animateY(2000)
    }

    override fun getPieChart(view: View,count:Count) {
        val listPie = ArrayList<PieEntry>()
        val listColors = ArrayList<Int>()
        listPie.add(PieEntry(count.case_active.toFloat(), view.context.getString(R.string.text_active)))
        listPie.add(PieEntry(count.case_recovered.toFloat(), view.context.getString(R.string.text_recovered)))
        listPie.add(PieEntry(count.case_death.toFloat(), view.context.getString(R.string.text_death)))

        listColors.add( view.context.getColor(R.color.text_confirmed_value))
        listColors.add( view.context.getColor(R.color.text_recovered_value))
        listColors.add( view.context.getColor (R.color.text_deceased_value))

        val pieDataSet = PieDataSet(listPie,"Pie Chart")
        pieDataSet.colors = listColors
        pieDataSet.valueTextColor= view.context.getColor(R.color.app_text_color)
        pieDataSet.valueTextSize = 15f
        view.pieChart.data = PieData(pieDataSet)
        //view.pieChart.legend.textSize = 15f
        view.pieChart.legend.formToTextSpace= 5f
        view.pieChart.legend.textColor = Color.WHITE
        pieDataSet.valueFormatter = PercentFormatter(view.pieChart)
        view.pieChart.setUsePercentValues(true)
        view.pieChart.isDrawHoleEnabled = false
        view.pieChart.description.isEnabled = false
        view.pieChart.setDrawEntryLabels(false)
        view.pieChart.animateY(3000)
    }
}


