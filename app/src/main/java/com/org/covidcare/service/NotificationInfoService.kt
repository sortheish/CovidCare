package com.org.covidcare.service

import android.content.Context
import android.view.View
import com.google.firebase.database.DataSnapshot
import com.org.covidcare.model.NotificationInfo

/**
 * Created by ishwari s on 8/17/2020.
 */
interface NotificationInfoService {
    interface NotificationInfoModel {
        fun getNotificationList(dataSnapshot: DataSnapshot,context: Context)
        fun getDataFromServer(notification_data_id:String?,complete: (NotificationInfo) -> Unit)
        fun isPhoneNumberValidate(phoneNumber:String,complete: (Boolean) -> Unit)
        fun updateLoginStatus()
        fun clearData()
    }

    interface NotificationInfoView{
        fun setNotificationDetailsData(view: View)
    }

    interface NotificationInfoPresenter {
        fun getNotificationList(dataSnapshot: DataSnapshot,context: Context)
        fun getDataFromServer(notification_data_id:String?,complete: (NotificationInfo) -> Unit)
        fun isPhoneNumberValidate(phoneNumber:String,complete: (Boolean) -> Unit)
        fun updateLoginStatus()
        fun setNotificationDetailsData(view: View)
        fun clearData()
    }
}