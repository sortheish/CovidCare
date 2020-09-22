package com.org.covidcare.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.org.covidcare.R
import com.org.covidcare.model.Query
import com.org.covidcare.utilities.App
import kotlinx.android.synthetic.main.fragment_query.*
import kotlinx.android.synthetic.main.fragment_query.view.*


class QueryFragment : Fragment(), View.OnClickListener {

    private val dbPath = "queries"

    companion object {
        fun newInstance(): QueryFragment = QueryFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(
            R.layout.fragment_query, container,
            false
        )
        view.findViewById<EditText>(R.id.editTextPersonName).setText(App.prefs.userName)
        view.findViewById<EditText>(R.id.editTextEmpId).setText(App.prefs.userEmpID)
        view.findViewById<EditText>(R.id.editTextNumber).setText(App.prefs.userMobileNumber)
        view.btn_drop_query.setOnClickListener(this)

        return view
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_drop_query ->
                validateFields()
        }
    }

    private fun validateFields() {
        if (editTextPersonName.text.toString().isEmpty() ||
            editTextEmpId.text.toString().isEmpty() ||
            editTextNumber.text.toString().isEmpty() ||
            editTextQuery.text.toString().isEmpty()
        ) {
            Toast.makeText(context, "Please enter all the data.", Toast.LENGTH_SHORT).show()
        } else {
            submitQuery()
        }
    }

    private fun submitQuery() {
        val query = Query(
            App.prefs.userName,
           App.prefs.userEmpID,
            editTextNumber.text.toString(),
            editTextQuery.text.toString(),
            App.prefs.userEmailAddress,
            System.currentTimeMillis().toString()
        )

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference(dbPath)
        val key = myRef.push().key
        val queryValues = query.toMap()

        myRef.child(key!!).setValue(queryValues).addOnSuccessListener {
            Toast.makeText(context, "Query submitted!", Toast.LENGTH_SHORT).show()
            activity?.onBackPressed()
        }.addOnFailureListener {
            Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show()
        }
    }
}