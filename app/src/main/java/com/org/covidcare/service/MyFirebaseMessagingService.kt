package com.org.covidcare.service


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.org.covidcare.R
import com.org.covidcare.ui.DashboardActivity
import com.org.covidcare.utilities.*
import java.util.*


/**
 * Created by ishwari s on 8/17/2020.
 */
class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        val uniqueID: String = UUID.randomUUID().toString()
        val database = Firebase.database
        val databaseRef = database.getReference(FIREBASE_DATABASE_APP_USERS)
        App.prefs.uniqueID = uniqueID
        databaseRef.child(uniqueID).child(DEVICE_ID).setValue(uniqueID)
        databaseRef.child(uniqueID).child(FIREBASE_FCM_TOKEN).setValue(token)
        databaseRef.child(uniqueID).child(FIREBASE_IS_LOGGED_IN).setValue(App.prefs.isLoggedIn)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage.notification != null) {
            showNotification(
                remoteMessage.notification?.title,
                remoteMessage.notification?.body,
                remoteMessage.data.getValue(FIREBASE_NEWSID)
            )
        }

        if (remoteMessage.notification?.body!!.isNotEmpty()) {
            Log.e(
                "MyFirebaseMessagingService",
                "Message data payload: ${remoteMessage.data.containsKey(FIREBASE_NEWSID)}{${remoteMessage.data.getValue(
                    FIREBASE_NEWSID
                )}"
            )
        }
    }

    private fun showNotification(title: String?, body: String?, notification_data_id: String) {
        val notificationIntent = Intent(this, DashboardActivity::class.java)
        notificationIntent.putExtra(
            getString(R.string.notification_intent_extra_name),
            getString(R.string.notification_intent_extra_value)
        )
        notificationIntent.putExtra(
            getString(R.string.notification_channel_id),
            notification_data_id
        )
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(
            this, 0, notificationIntent,
            PendingIntent.FLAG_ONE_SHOT
        )

        val mChannel = NotificationChannel(
            getString(R.string.notification_channel_id),
            getString(R.string.notification_channel_name),
            NotificationManager.IMPORTANCE_HIGH
        )
        val notification: Notification =
            Notification.Builder(this, getString(R.string.notification_channel_id))
                .setSmallIcon(R.drawable.masks_white)
                .setColor(getColor(R.color.text_tab_change_color))
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setChannelId(getString(R.string.notification_channel_id))
                .setContentIntent(pendingIntent).build()

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(mChannel)
        notificationManager.notify(0, notification)
    }
}