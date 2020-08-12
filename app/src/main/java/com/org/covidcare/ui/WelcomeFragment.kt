package com.org.covidcare.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.org.covidcare.R
import com.org.covidcare.utilities.CovidData
import kotlinx.android.synthetic.main.fragment_welcome.view.*

/**
 * Created by ishwari s on 7/9/2020.
 */
private const val USER_NAME = "userName"

class WelcomeFragment : Fragment(),View.OnClickListener {
    private var userName: String? = null

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
        view.text_user_name.text = userName
        view.btn_logout.setOnClickListener(this)
        return view
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_logout -> {
                FirebaseAuth.getInstance().signOut()
                openFragment(AboutFragment.newInstance())
                closeFragment()
                CovidData.logout()
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
}