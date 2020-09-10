package com.org.covidcare.service

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.org.covidcare.model.NotificationInfo
import com.org.covidcare.utilities.*

/**
 * Created by ishwari s on 8/17/2020.
 */
class NotificationInfoModel : NotificationInfoService.NotificationInfoModel {
    /**
     * Get all email data from server(firebase) from database FIREBASE_DATABASE_COVID_CARE
     * to show the data in list.
     */
    override fun getNotificationList(dataSnapshot: DataSnapshot) {
        for (artistSnapshot in dataSnapshot.children) {
            val senderGroup: String = artistSnapshot.child(FIREBASE_TITLE).value.toString()
            val date: Long = artistSnapshot.child(FIREBASE_DATE).value as Long
            val title: String = artistSnapshot.child(FIREBASE_SUBJECT).value.toString()
            val image: String = artistSnapshot.child(FIREBASE_PHOTO).value.toString()
            val content: String = artistSnapshot.child(FIREBASE_CONTENT).value.toString()
            val link: String = artistSnapshot.child(FIREBASE_LINK).value.toString()
            CovidData.notifications.add(
                NotificationInfo(
                    senderGroup,
                    date,
                    title,
                    image,
                    content,
                    link
                )
            )
        }
    }

    /**
     * Get email data from server(firebase) from database FIREBASE_DATABASE_COVID_CARE
     * for specific id or child of database.
     */
    override fun getDataFromServer(
        notification_data_id: String?,
        complete: (NotificationInfo) -> Unit
    ) {
        val database = Firebase.database
        val databaseRef = database.getReference(FIREBASE_DATABASE_COVID_CARE)
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.hasChild(notification_data_id!!)) {
                    val senderGroup: String = dataSnapshot.child(notification_data_id)
                        .child(FIREBASE_TITLE).value.toString()
                    val date: Long =
                        dataSnapshot.child(notification_data_id).child(FIREBASE_DATE).value as Long
                    val title: String = dataSnapshot.child(notification_data_id)
                        .child(FIREBASE_SUBJECT).value.toString()
                    val image: String = dataSnapshot.child(notification_data_id)
                        .child(FIREBASE_PHOTO).value.toString()
                    val content: String = dataSnapshot.child(notification_data_id)
                        .child(FIREBASE_CONTENT).value.toString()
                    val link: String = dataSnapshot.child(notification_data_id)
                        .child(FIREBASE_LINK).value.toString()
                    complete(NotificationInfo(senderGroup, date, title, image, content, link))
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    /**
     * Check the user number is register or not using database FIREBASE_DATABASE_CYBAGIANS
     */
    override fun isPhoneNumberValidate(phoneNumber: String, complete: (Boolean) -> Unit) {
        val database = Firebase.database
        val databaseRef = database.getReference(FIREBASE_DATABASE_CYBAGIANS)
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.hasChild(phoneNumber)) {
                    App.prefs.userEmailAddress = dataSnapshot.child(phoneNumber)
                        .child(FIREBASE_CYBAGIANS_EMAIL_ID).value.toString()
                    Log.e("email",dataSnapshot.child(phoneNumber)
                        .child(FIREBASE_CYBAGIANS_EMAIL_ID).value.toString())
                    App.prefs.userName = dataSnapshot.child(phoneNumber)
                        .child(FIREBASE_CYBAGIANS_USERNAME).value.toString()
                    Log.e("userName",dataSnapshot.child(phoneNumber)
                        .child(FIREBASE_CYBAGIANS_USERNAME).value.toString())
                    App.prefs.userEmpID = dataSnapshot.child(phoneNumber)
                        .child(FIREBASE_CYBAGIANS_EMP_ID).value.toString()
                    Log.e("userName1", dataSnapshot.child(phoneNumber)
                        .child(FIREBASE_CYBAGIANS_EMP_ID).value.toString())
                    complete(true)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                complete(false)
            }
        })
    }

    /**
     * Update the login status of user in database FIREBASE_DATABASE_REGI_USER
     */
    override fun updateLoginStatus() {
        val database = Firebase.database
        val databaseRef = database.getReference(FIREBASE_DATABASE_APP_USERS)
        if (App.prefs.uniqueID?.isNotEmpty()!!) {
            databaseRef.child(App.prefs.uniqueID!!).child(FIREBASE_IS_LOGGED_IN)
                .setValue(App.prefs.isLoggedIn)
        }
    }

    override fun clearData() {
        CovidData.notifications.clear()
    }

}