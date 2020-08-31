package com.org.covidcare.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.org.covidcare.R
import com.org.covidcare.adapter.NotificationListAdapter
import com.org.covidcare.presenter.NotificationInfoPresenter
import com.org.covidcare.service.NotificationInfoService
import com.org.covidcare.utilities.*

/**
 * Created by ishwari s on 8/14/2020.
 */
class NotificationFragment : Fragment(), NotificationInfoService.NotificationInfoView {
    private var notificationListAdapter: NotificationListAdapter? = null
    private var notificationInfoPresenter: NotificationInfoPresenter? = null

    companion object {
        fun newInstance(): NotificationFragment = NotificationFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(
            R.layout.fragment_notification, container,
            false
        )
        notificationInfoPresenter = NotificationInfoPresenter(this)
        notificationInfoPresenter?.setNotificationDetailsData(view)

        val database = Firebase.database
        val databaseRef = database.getReference(FIREBASE_DATABASE_COVID_CARE)
        databaseRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                notificationInfoPresenter?.clearData()
                notificationInfoPresenter?.getNotificationList(dataSnapshot)
                notificationListAdapter?.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Notification", "Failed to read value.", error.toException())
            }
        })
        return view
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.fragment_container, fragment)
        transaction?.addToBackStack(null)
        transaction?.commit()
    }

    override fun setNotificationDetailsData(view: View) {
        notificationListAdapter =
            NotificationListAdapter(CovidData.notifications) { notificationInfo ->
                openFragment(NotificationDetailsFragment.newInstance(notificationInfo))
            }
        val layoutManager = LinearLayoutManager(activity)
        val list = view.findViewById<RecyclerView>(R.id.list_notifications)
        list.layoutManager = layoutManager
        list.adapter = notificationListAdapter
    }
}