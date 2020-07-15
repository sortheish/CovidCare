package com.org.covidcare.ui

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.org.covidcare.R
import com.org.covidcare.view.AboutService
import com.org.covidcare.view.AboutServicePresenter

/**
 * Created by ishwari s on 6/17/2020.
 */
class AboutFragment : Fragment(),AboutService.AboutView,View.OnClickListener{
    private lateinit var dialog:Dialog
    private var aboutPresenter: AboutServicePresenter? = null
    companion object {
        fun newInstance(): AboutFragment = AboutFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(
        R.layout.fragment_about, container,
        false
        )
        aboutPresenter = AboutServicePresenter(this)
        view.findViewById<Button>(R.id.btn_sign_in).setOnClickListener(this)
        return view
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_sign_in -> {
                aboutPresenter?.setDialog(v)
            }
            R.id.btn_dialog_go ->{
                openFragment(WelcomeFragment.newInstance("name"))
                dialog.dismiss()
            }
            R.id.btn_dialog_cancle->{
                dialog.dismiss()
            }
        }
    }


    override fun setDialog(view: View) {
        dialog = Dialog(view.context)
        dialog.setCancelable(true)
        // this.activity =
        val dialogView: View =
            activity!!.layoutInflater.inflate(R.layout.login_dialog_layout, null)
        dialog.setContentView(dialogView)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogView.findViewById<Button>(R.id.btn_dialog_go).setOnClickListener(this)
        dialogView.findViewById<Button>(R.id.btn_dialog_cancle).setOnClickListener(this)
        dialog.show()
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.fragment_container, fragment)
        transaction?.addToBackStack(null)
        transaction?.commit()
    }

}