package com.org.covidcare.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.org.covidcare.R
import com.org.covidcare.model.Query
import kotlinx.android.synthetic.main.fragment_query.*
import kotlinx.android.synthetic.main.fragment_query.view.*


class QueryFragment : Fragment(), View.OnClickListener {

    private val TAG = "QueryFragment"
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


        /* myRef.addValueEventListener(object : ValueEventListener {
             override fun onDataChange(dataSnapshot: DataSnapshot) {
                 // This method is called once with the initial value and again
                 // whenever data at this location is updated.
                 Log.d(TAG, "value for queries - " + dataSnapshot.value.toString())
             }

             override fun onCancelled(error: DatabaseError) {
                 Log.w(TAG, "Failed to read value.", error.toException())
             }
         })
 */
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
            editTextPersonName.text.toString(),
            editTextEmpId.text.toString(),
            editTextNumber.text.toString(),
            editTextQuery.text.toString(),
            "sample.email@aaa.com",  //todo to replace with actual email value
            System.currentTimeMillis().toString()
        )

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference(dbPath)

        val key = myRef.push().key

        val queryValues = query.toMap()
        Log.w(TAG, "Key - ." + key.toString())
        Log.w(TAG, "queryValues - ." + queryValues.toString())

        myRef.child(key!!).setValue(queryValues).addOnSuccessListener {
            Toast.makeText(context, "Query submitted!", Toast.LENGTH_SHORT).show()
            activity?.onBackPressed()
        }.addOnFailureListener {
            Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show()
        }
    }
}