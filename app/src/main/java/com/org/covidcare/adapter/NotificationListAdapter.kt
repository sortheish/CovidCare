package com.org.covidcare.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.org.covidcare.R
import com.org.covidcare.model.NotificationInfo
import com.org.covidcare.utilities.CovidData
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by ishwari s on 8/14/2020.
 */
class NotificationListAdapter(
    private val notificationList: ArrayList<NotificationInfo>,
    val itemClick: (NotificationInfo) -> Unit
) :
    RecyclerView.Adapter<NotificationListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_of_notification_data, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return notificationList.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        notificationList.sortByDescending { it.date }
        holder.bindDistrictData(notificationList[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textSenderGroup =
            itemView.findViewById<TextView>(R.id.text_sender_group)!!
        private val textDate =
            itemView.findViewById<TextView>(R.id.date)!!
        private val textTitle =
            itemView.findViewById<TextView>(R.id.title)!!

        fun bindDistrictData(notificationInfo: NotificationInfo) {
            textSenderGroup.text = notificationInfo.sender_group
            CovidData.getDate(notificationInfo.date!!)
            textTitle.text = notificationInfo.title
            if (CovidData.getDate(notificationInfo.date) == getCurrentDate()) {
                textDate.text = CovidData.getTime(notificationInfo.date)
            } else {
                textDate.text = CovidData.getDate(notificationInfo.date)
            }
            itemView.setOnClickListener { itemClick(notificationInfo) }
        }
    }

    private fun getCurrentDate(): String {
        return SimpleDateFormat("ddMMM", Locale.US).format(System.currentTimeMillis()).toString()
    }
}