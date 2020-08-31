package com.org.covidcare.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.org.covidcare.R
import com.org.covidcare.model.NotificationInfo
import com.org.covidcare.presenter.NotificationInfoPresenter
import com.org.covidcare.service.NotificationInfoService
import com.org.covidcare.utilities.CovidData
import com.org.covidcare.utilities.FIREBASE_PHOTO_BASE_URL
import com.org.covidcare.utilities.FIREBASE_PHOTO_END_URL
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class NotificationDetailsFragment : Fragment(), NotificationInfoService.NotificationInfoView {
    private lateinit var objNotificationInfo: NotificationInfo
    private var notificationInfoPresenter: NotificationInfoPresenter? = null

    companion object {
        private const val ARG_PARAM = "notificationInfoObject"
        fun newInstance(notificationInfo: NotificationInfo) =
            NotificationDetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PARAM, notificationInfo)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            objNotificationInfo = it.getParcelable(ARG_PARAM)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_notification_details, container, false)

        notificationInfoPresenter = NotificationInfoPresenter(this)
        notificationInfoPresenter?.setNotificationDetailsData(view)

        return view
    }

    private fun getCurrentDate(): String {
        return SimpleDateFormat("ddMMM", Locale.US).format(System.currentTimeMillis()).toString()
    }

    override fun setNotificationDetailsData(view: View) {
        view.findViewById<TextView>(R.id.noti_details_title).text = objNotificationInfo.title
        view.findViewById<TextView>(R.id.noti_details_sender).text =
            objNotificationInfo.sender_group
        view.findViewById<TextView>(R.id.notif_details_content).text = objNotificationInfo.content
        view.findViewById<TextView>(R.id.notif_details_link_value).text = objNotificationInfo.link

        if (CovidData.getDate(objNotificationInfo.date!!) == getCurrentDate()) {
            view.findViewById<TextView>(R.id.noti_details_date).text = CovidData.getTime(
                objNotificationInfo.date!!
            )
        } else {
            view.findViewById<TextView>(R.id.noti_details_date).text = CovidData.getDate(
                objNotificationInfo.date!!
            )
        }
        val imageContent: ImageView = view.findViewById(R.id.notif_details_image)
        val url: String =
            FIREBASE_PHOTO_BASE_URL + objNotificationInfo.image + FIREBASE_PHOTO_END_URL
        Picasso.with(this.context).load(url).into(imageContent)
    }
}