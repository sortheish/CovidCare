package com.org.covidcare.presenter

import android.content.Context
import android.view.View
import com.google.firebase.database.DataSnapshot
import com.org.covidcare.model.NotificationInfo
import com.org.covidcare.service.NotificationInfoModel
import com.org.covidcare.service.NotificationInfoService

/**
 * Created by ishwari s on 8/17/2020.
 */
class NotificationInfoPresenter(notificationView: NotificationInfoService.NotificationInfoView) :
    NotificationInfoService.NotificationInfoPresenter {
    private var model = NotificationInfoModel()
    private var view = notificationView
    override fun getNotificationList(dataSnapshot: DataSnapshot,context: Context) {
        model.getNotificationList(dataSnapshot,context)
    }

    override fun getDataFromServer(
        notification_data_id: String?,
        complete: (NotificationInfo) -> Unit
    ) {
        model.getDataFromServer(notification_data_id, complete)
    }

    override fun isPhoneNumberValidate(phoneNumber: String, complete: (Boolean) -> Unit) {
        return model.isPhoneNumberValidate(phoneNumber, complete)
    }

    override fun updateLoginStatus() {
        model.updateLoginStatus()
    }

    override fun setNotificationDetailsData(view: View) {
        this.view.setNotificationDetailsData(view)
    }

    override fun clearData() {
        model.clearData()
    }
}