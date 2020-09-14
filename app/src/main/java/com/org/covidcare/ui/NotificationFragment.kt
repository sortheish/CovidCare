package com.org.covidcare.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.org.covidcare.R
import com.org.covidcare.adapter.NotificationListAdapter
import com.org.covidcare.handlers.DatabaseHandler
import com.org.covidcare.handlers.SwipeToDeleteHandler
import com.org.covidcare.model.NotificationInfo
import com.org.covidcare.presenter.NotificationInfoPresenter
import com.org.covidcare.service.NotificationInfoService
import com.org.covidcare.utilities.App
import com.org.covidcare.utilities.CovidData
import com.org.covidcare.utilities.FIREBASE_DATABASE_COVID_CARE

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
                notificationInfoPresenter?.getNotificationList(dataSnapshot,context!!)
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
    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                //closeFragment()
                openFragment(WelcomeFragment.newInstance(App.prefs.userName))
                //openFragment(WelcomeFragment())
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
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
        swipeToDelete(view)
    }

    private fun swipeToDelete(view: View){
        val swipeToDeleteCallback = object : SwipeToDeleteHandler(context) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                addDeletedNotificationIdToDatabase(CovidData.notifications[position].notification_id)
                CovidData.notifications.removeAt(position)
                notificationListAdapter!!.notifyItemRemoved(position)
            }
        }
            ItemTouchHelper(swipeToDeleteCallback).attachToRecyclerView(view.findViewById(R.id.list_notifications))
    }

    private fun addDeletedNotificationIdToDatabase(notificationId: String?) {
        if (notificationId != null) {
            DatabaseHandler(context).addNotificationIds(NotificationInfo(notificationId, "",0,"","","",""))
        }
    }
}