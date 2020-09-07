package com.org.covidcare.ui

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.org.covidcare.R
import com.org.covidcare.presenter.NotificationInfoPresenter
import com.org.covidcare.service.NotificationInfoService
import com.org.covidcare.utilities.App
import com.org.covidcare.utilities.CovidData.isValidPhone
import com.org.covidcare.view.AboutService
import com.org.covidcare.view.AboutServicePresenter
import kotlinx.android.synthetic.main.login_dialog_layout.*

/**
 * Created by ishwari s on 6/17/2020.
 */
class AboutFragment : Fragment(), AboutService.AboutView, View.OnClickListener,
    NotificationInfoService.NotificationInfoView {
    private lateinit var dialog: Dialog
    private var aboutPresenter: AboutServicePresenter? = null
    private var notificationInfoPresenter: NotificationInfoPresenter? = null
    private lateinit var auth: FirebaseAuth
    lateinit var verificationCode: String
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken

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
        notificationInfoPresenter = NotificationInfoPresenter(this)
        view.findViewById<Button>(R.id.btn_sign_in).setOnClickListener(this)
        return view
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_sign_in -> {
                aboutPresenter?.setDialog(v)
            }
            R.id.btn_dialog_go -> {
                if (dialog.editTextOtp.text.toString().isNotEmpty()) {
                    verifyCode(dialog.editTextOtp.text.toString())
                } else {
                    Toast.makeText(
                        activity,
                        getString(R.string.call_for_enter_otp),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
            R.id.btn_dialog_verify -> {
                Log.e("Button", "Click")
                if (dialog.text_user_name.text.toString().isNotEmpty()) {
                    if (isValidPhone(dialog.editTextNumber.text.toString())) {
                        notificationInfoPresenter?.isPhoneNumberValidate(dialog.editTextNumber.text.toString()) { isRegister ->
                            if (isRegister) {
                                dialog.textViewError.visibility = GONE
                                dialog.btn_dialog_verify.alpha = 0.5f
                                dialog.btn_dialog_verify.isEnabled = false
                                dialog.btn_dialog_resend.alpha = 1.0f
                                dialog.btn_dialog_resend.isEnabled = true
                                dialog.text_user_name.isEnabled = false
                                dialog.editTextNumber.isEnabled = false
                                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                    "+91" + dialog.editTextNumber.text.toString(),
                                    60,
                                    java.util.concurrent.TimeUnit.SECONDS,
                                    activity!!,
                                    mCallbacks
                                )
                            } else {
                                dialog.textViewError.visibility = View.VISIBLE
                            }
                        }
                    } else {
                        Toast.makeText(
                            activity,
                            getString(R.string.call_for_proper_mobile_number),
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                } else {
                    Toast.makeText(
                        activity,
                        getString(R.string.call_for_user_name),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
            R.id.btn_dialog_cancle -> {
                dialog.dismiss()
            }
            R.id.btn_dialog_resend -> {
                if (dialog.text_user_name.text.toString().isNotEmpty()) {
                    if (isValidPhone(dialog.editTextNumber.text.toString())) {
                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            "+91" + dialog.editTextNumber.text.toString(),
                            60,
                            java.util.concurrent.TimeUnit.SECONDS,
                            activity!!,
                            mCallbacks,
                            resendToken
                        )
                    } else {
                        Toast.makeText(
                            activity,
                            getString(R.string.call_for_proper_mobile_number),
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                } else {
                    Toast.makeText(
                        activity,
                        getString(R.string.call_for_user_name),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }
    }


    @SuppressLint("InflateParams")
    override fun setDialog(view: View) {
        dialog = Dialog(view.context)
        dialog.setCancelable(true)
        val dialogView: View =
            activity!!.layoutInflater.inflate(R.layout.login_dialog_layout, null)
        dialog.setContentView(dialogView)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.btn_dialog_resend.alpha = 0.5f
        dialogView.findViewById<Button>(R.id.btn_dialog_go).setOnClickListener(this)
        dialogView.findViewById<Button>(R.id.btn_dialog_cancle).setOnClickListener(this)
        dialogView.findViewById<Button>(R.id.btn_dialog_verify).setOnClickListener(this)
        dialog.show()
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.fragment_container, fragment,null)
        transaction?.addToBackStack(null)
        transaction?.commit()
    }

    private fun verifyCode(code: String) {
        auth = FirebaseAuth.getInstance()
        if (code.isNotBlank())
            try {
                val credential = PhoneAuthProvider.getCredential(verificationCode, code)
                signInWithPhoneAuthCredential(credential)
            } catch (e: Exception) {
                Toast.makeText(activity, e.toString(), Toast.LENGTH_LONG).show()
            }
    }

    private val mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks =
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                val code: String? = phoneAuthCredential.smsCode
                if (code != null) {
                    dialog.editTextOtp.setText(code)
                }
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(
                    activity,
                    getString(R.string.verification_failed),
                    Toast.LENGTH_SHORT
                )
                    .show()
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                super.onCodeSent(verificationId, token)
                verificationCode = verificationId
                resendToken = token

                dialog.editTextNumber.visibility = GONE
               // dialog.textViewDialog.text = getString(R.string.text_otp_verification)
                dialog.text_user_name.visibility = GONE
                dialog.btn_dialog_verify.visibility = GONE
                dialog.btn_dialog_resend.visibility = GONE
                dialog.btn_dialog_go.visibility = View.VISIBLE
                dialog.editTextOtp.visibility = View.VISIBLE

            }
        }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential).addOnCompleteListener(activity!!) { task ->
            if (task.isSuccessful) {
                App.prefs.isLoggedIn = true
                notificationInfoPresenter?.updateLoginStatus()
                App.prefs.userMobileNumber = dialog.editTextNumber.text.toString()
                App.prefs.userName = dialog.text_user_name.text.toString()
                openFragment(WelcomeFragment.newInstance(dialog.text_user_name.text.toString()))
            } else {
                when (task.exception) {
                    is FirebaseAuthInvalidCredentialsException -> {
                        Toast.makeText(
                            activity,
                            getString(R.string.firebase_invalid_request),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    is FirebaseTooManyRequestsException -> {
                        Toast.makeText(
                            activity,
                            getString(R.string.firebase_SMS_Quota),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    else -> Toast.makeText(
                        activity,
                        getString(R.string.firebase_request_failed),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            dialog.dismiss()
        }
    }

    override fun setNotificationDetailsData(view: View) {
        TODO("Not yet implemented")
    }
}
