package com.org.covidcare.ui

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.org.covidcare.R
import com.org.covidcare.presenter.NotificationInfoPresenter
import com.org.covidcare.service.NotificationInfoService
import com.org.covidcare.utilities.CovidData
import kotlinx.android.synthetic.main.fragment_welcome.view.*
import kotlinx.android.synthetic.main.login_dialog_layout.*

/**
 * Created by ishwari s on 7/9/2020.
 */
private const val USER_NAME = "userName"
  
class WelcomeFragment : Fragment(),View.OnClickListener,NotificationInfoService.NotificationInfoView {
private lateinit var dialog: Dialog

    private var userName: String? = null
    private var notificationInfoPresenter: NotificationInfoPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            userName = it.getString(USER_NAME)
        }
    }

    companion object {
        fun newInstance(value: String?) =
            WelcomeFragment().apply {
                arguments = Bundle().apply {
                    putString(USER_NAME, value)
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(
            R.layout.fragment_welcome, container,
            false
        )

        notificationInfoPresenter = NotificationInfoPresenter(this)
        view.text_user_name.text = userName
        view.btn_logout.setOnClickListener(this)
        view.btn_help_line.setOnClickListener(this)
        view.btn_feed.setOnClickListener(this)
        return view
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_logout -> {
                FirebaseAuth.getInstance().signOut()
                openFragment(AboutFragment.newInstance())
                closeFragment()
                CovidData.logout()
                notificationInfoPresenter?.updateLoginStatus()
            }
            R.id.btn_feed -> {
                openFragment(NotificationFragment.newInstance())
            }

            R.id.btn_help_line -> {
                showDialog(v);
            }

            R.id.btn_just_call -> {
                //Toast.makeText(v.context, "Just a call", Toast.LENGTH_SHORT).show()
                openFragment(HelplineFragment.newInstance())
                dialog.dismiss()
            }

            R.id.btn_drop_query -> {
                Toast.makeText(v.context, "Drop a query - Work in progress", Toast.LENGTH_SHORT).show()
                //openFragment(QueryFragment.newInstance())
                dialog.dismiss()
            }
        }
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.fragment_container, fragment)
        transaction?.addToBackStack(null)
        transaction?.commit()
    }

    private fun closeFragment() {
        activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
    }


    private fun showDialog(view: View) {
        dialog = Dialog(view.context)
        dialog.setCancelable(true)
        val dialogView: View =
            activity!!.layoutInflater.inflate(R.layout.helpline_dialog_layout, null)
        dialog.setContentView(dialogView)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogView.findViewById<Button>(R.id.btn_just_call).setOnClickListener(this)
        dialogView.findViewById<Button>(R.id.btn_drop_query).setOnClickListener(this)
        dialog.show()

    override fun setNotificationDetailsData(view: View) {
        TODO("Not yet implemented")
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }
}