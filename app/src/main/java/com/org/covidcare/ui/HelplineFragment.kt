package com.org.covidcare.ui

import android.os.Bundle
import android.text.util.Linkify
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.database.*
import com.org.covidcare.R
import kotlinx.android.synthetic.main.fragment_guide.*
import kotlinx.android.synthetic.main.fragment_helpline.*
import kotlinx.android.synthetic.main.fragment_helpline.view.*


class HelplineFragment : Fragment(), View.OnClickListener {
    private val TAG = "HelplineFragment"
    private lateinit var database: DatabaseReference
    private val dbPath = "helpline"

    private val city1 = "PUNE"
    private val city2 = "HYD"
    private val city3 = "GNR"

    private var city1Active = false
    private var city2Active = false
    private var city3Active = false


    companion object {
        fun newInstance(): HelplineFragment = HelplineFragment()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(
            R.layout.fragment_helpline, container,
            false
        )

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference(dbPath)

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val valueCity1 = dataSnapshot.child(city1).value.toString()
                val valueCity2 = dataSnapshot.child(city2).value.toString()
                val valueCity3 = dataSnapshot.child(city3).value.toString()
                Log.d(TAG, "value for $city1 is: $valueCity1")
                Log.d(TAG, "value for $city2 is: $valueCity2")
                Log.d(TAG, "value for $city3 is: $valueCity3")

                showHelplineData(valueCity1, valueCity2, valueCity3)
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })

        view.imageView_arrow1.setOnClickListener(this)
        view.imageView_arrow2.setOnClickListener(this)
        view.imageView_arrow3.setOnClickListener(this)

        return view
    }

    private fun showHelplineData(valueCity1: String, valueCity2: String, valueCity3: String) {

        rl_city1.visibility = View.VISIBLE
        rl_city2.visibility = View.VISIBLE
        rl_city3.visibility = View.VISIBLE
        textView_helplines.text = "Helpline Numbers"

        textView_header1.text = city1
        textView_details1.text = addNewLines(valueCity1)

        textView_header2.text = city2
        textView_details2.text = addNewLines(valueCity2)

        textView_header3.text = city3
        textView_details3.text = addNewLines(valueCity3)

        Linkify.addLinks(textView_details1, Linkify.PHONE_NUMBERS)
        Linkify.addLinks(textView_details2, Linkify.PHONE_NUMBERS)
        Linkify.addLinks(textView_details3, Linkify.PHONE_NUMBERS)
    }

    private fun addNewLines(details: String): String? {
        val ls = System.lineSeparator()
        return details.replace("\\n", ls)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.imageView_arrow1, R.id.imageView_arrow2, R.id.imageView_arrow3 ->
                toggleDetails(v.id)
        }
    }

    private fun toggleDetails(id: Int) {
        when (id) {
            R.id.imageView_arrow1 -> {
                expandCollapse(imageView_arrow1, textView_details1, city1Active)
                city1Active = !city1Active
            }

            R.id.imageView_arrow2 -> {
                expandCollapse(imageView_arrow2, textView_details2, city2Active)
                city2Active = !city2Active
            }

            R.id.imageView_arrow3 -> {
                expandCollapse(imageView_arrow3, textView_details3, city3Active)
                city3Active = !city3Active
            }
        }
    }

    private fun expandCollapse(
        imageViewArrow: ImageView?,
        textViewDetails: TextView?,
        cityActive: Boolean
    ) {
        if(cityActive){
            imageViewArrow!!.setBackgroundResource(android.R.drawable.arrow_down_float)
            textViewDetails!!.visibility = View.GONE
        }
        else{
            imageViewArrow!!.setBackgroundResource(android.R.drawable.arrow_up_float)
            textViewDetails!!.visibility = View.VISIBLE
        }
    }
}