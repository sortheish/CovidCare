package com.org.covidcare.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import com.google.firebase.database.FirebaseDatabase
import com.org.covidcare.R
import com.org.covidcare.model.Query
import com.org.covidcare.model.Status
import com.org.covidcare.utilities.App
import kotlinx.android.synthetic.main.fragment_query.*
import kotlinx.android.synthetic.main.fragment_self_assist.*
import kotlinx.android.synthetic.main.fragment_self_assist.view.*
import java.util.ArrayList

class SelfAssistFragment : Fragment(), View.OnClickListener {

    private lateinit var viewOfLayout: View
    var questionAnswerMap = HashMap<Int, String>()
    lateinit var questionnaire: String

    companion object {
        fun newInstance(): SelfAssistFragment = SelfAssistFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewOfLayout = inflater.inflate(
            R.layout.fragment_self_assist, container,
            false
        )

        hideView()
        attachButtonEvents()
        askQuestion1()

        return viewOfLayout
    }

    private fun scrollToEnd() {
        viewOfLayout.scrollView.post {
            viewOfLayout.scrollView.fullScroll(View.FOCUS_DOWN)
        }
    }

    private fun hideView() {
        viewOfLayout.row_question1.visibility = View.GONE
        viewOfLayout.row_question2.visibility = View.GONE
        viewOfLayout.row_question3.visibility = View.GONE
        viewOfLayout.row_question4.visibility = View.GONE
        viewOfLayout.row_question5.visibility = View.GONE
        viewOfLayout.row_question6.visibility = View.GONE
        viewOfLayout.row_result_low_risk.visibility = View.GONE
        viewOfLayout.row_result_high_risk.visibility = View.GONE
        viewOfLayout.question1_next.visibility = View.GONE
        viewOfLayout.question2_next.visibility = View.GONE
    }

    private fun attachButtonEvents() {
        viewOfLayout.question1_option1.setOnClickListener(this)
        viewOfLayout.question1_option2.setOnClickListener(this)
        viewOfLayout.question1_option3.setOnClickListener(this)
        viewOfLayout.question1_option4.setOnClickListener(this)
        viewOfLayout.question1_next.setOnClickListener(this)
        viewOfLayout.question1_option_none.setOnClickListener(this)
        viewOfLayout.question2_option1.setOnClickListener(this)
        viewOfLayout.question2_option2.setOnClickListener(this)
        viewOfLayout.question2_option3.setOnClickListener(this)
        viewOfLayout.question2_option4.setOnClickListener(this)
        viewOfLayout.question2_option5.setOnClickListener(this)
        viewOfLayout.question2_next.setOnClickListener(this)
        viewOfLayout.question2_option_none.setOnClickListener(this)
        viewOfLayout.question3_option1.setOnClickListener(this)
        viewOfLayout.question3_option2.setOnClickListener(this)
        viewOfLayout.question4_option1.setOnClickListener(this)
        viewOfLayout.question4_option2.setOnClickListener(this)
        viewOfLayout.question4_option_none.setOnClickListener(this)
        viewOfLayout.question5_option1.setOnClickListener(this)
        viewOfLayout.question5_option2.setOnClickListener(this)
        viewOfLayout.question5_option3.setOnClickListener(this)
        viewOfLayout.question5_option_none.setOnClickListener(this)
        viewOfLayout.question6_option1.setOnClickListener(this)
        viewOfLayout.question6_option2.setOnClickListener(this)
        viewOfLayout.question6_option3.setOnClickListener(this)
        viewOfLayout.low_risk_option1.setOnClickListener(this)
        viewOfLayout.high_risk_option1.setOnClickListener(this)
        viewOfLayout.high_risk_option2.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v!!.id) {

            //Question 1
            R.id.question1_option1 -> {
                question1Answered(question1_option1)
            }
            R.id.question1_option2 -> {
                question1Answered(question1_option2)
            }
            R.id.question1_option3 -> {
                question1Answered(question1_option3)
            }
            R.id.question1_option4 -> {
                question1Answered(question1_option4)
            }
            R.id.question1_next -> {
                updateAnswersQ1()
            }
            R.id.question1_option_none -> {
                questionAnswerMap[1] = question1_option_none.text.toString()
                displayAnswer1()
            }

            //Question 2
            R.id.question2_option1 -> {
                question2Answered(question2_option1)
            }
            R.id.question2_option2 -> {
                question2Answered(question2_option2)
            }
            R.id.question2_option3 -> {
                question2Answered(question2_option3)
            }
            R.id.question2_option4 -> {
                question2Answered(question2_option4)
            }
            R.id.question2_option5 -> {
                question2Answered(question2_option5)
            }
            R.id.question2_next -> {
                updateAnswersQ2()
            }
            R.id.question2_option_none -> {
                questionAnswerMap[2] = question2_option_none.text.toString()
                displayAnswer2()
            }

            //Question 3
            R.id.question3_option1 -> {
                question3Answered(question3_option1.text.toString())
                showHighRisk()
            }
            R.id.question3_option2 -> {
                question3Answered(question3_option2.text.toString())
                askQuestion4()
            }

            //Question 4
            R.id.question4_option1 -> {
                question4Answered(question4_option1.text.toString())
                askQuestion6()
            }
            R.id.question4_option2 -> {
                question4Answered(question4_option2.text.toString())
                askQuestion6()
            }
            R.id.question4_option_none -> {
                question4Answered(question4_option_none.text.toString())
                showLowRisk()
            }

            //Question 5
            R.id.question5_option1 -> {
                question5Answered(question5_option1.text.toString())
            }
            R.id.question5_option2 -> {
                question5Answered(question5_option2.text.toString())
            }
            R.id.question5_option3 -> {
                question5Answered(question5_option3.text.toString())
            }
            R.id.question5_option_none -> {
                question5Answered(question5_option_none.text.toString())
            }

            //Question 6
            R.id.question6_option1 -> {
                question6Answered(question6_option1.text.toString())
            }
            R.id.question6_option2 -> {
                question6Answered(question6_option2.text.toString())
            }
            R.id.question6_option3 -> {
                question6Answered(question6_option3.text.toString())
            }


            R.id.low_risk_option1 -> {
                lowRiskOptionClicked()
            }
            R.id.high_risk_option1 -> {
                highRiskConfirmClicked()
            }
            R.id.high_risk_option2 -> {
                highRiskLaterClicked()
            }
        }
    }


    private fun askQuestion1() {
        viewOfLayout.row_question1.visibility = View.VISIBLE
        scrollToEnd()
    }

    private fun question1Answered(optionButton: Button) {
        optionButton.isSelected = !optionButton.isSelected
        updateSelectionQ1()
    }

    private fun updateSelectionQ1() {
        if (question1_option1.isSelected || question1_option2.isSelected
            || question1_option3.isSelected || question1_option4.isSelected
        ) {
            question1_option_none.visibility = View.GONE
            question1_next.visibility = View.VISIBLE
        } else {
            question1_option_none.visibility = View.VISIBLE
            question1_next.visibility = View.GONE
        }
    }

    private fun updateAnswersQ1() {
        val answers = ArrayList<String>()
        if (question1_option1.isSelected) answers.add(question1_option1.text.toString())
        if (question1_option2.isSelected) answers.add(question1_option2.text.toString())
        if (question1_option3.isSelected) answers.add(question1_option3.text.toString())
        if (question1_option4.isSelected) answers.add(question1_option4.text.toString())

        questionAnswerMap[1] = answers.joinToString()
        displayAnswer1()
    }

    private fun displayAnswer1() {
        group_q1_options.visibility = View.GONE
        question1_option_none.visibility = View.GONE
        question1_next.visibility = View.GONE
        question1_answer.text = questionAnswerMap[1]
        question1_answer.visibility = View.VISIBLE
        askQuestion2()
    }

    private fun askQuestion2() {
        viewOfLayout.row_question2.visibility = View.VISIBLE
        scrollToEnd()
    }

    private fun question2Answered(optionButton: Button) {
        optionButton.isSelected = !optionButton.isSelected
        updateSelectionQ2()
    }

    private fun updateSelectionQ2() {
        if (question2_option1.isSelected || question2_option2.isSelected
            || question2_option3.isSelected || question2_option4.isSelected
            || question2_option5.isSelected
        ) {
            question2_option_none.visibility = View.GONE
            question2_next.visibility = View.VISIBLE
        } else {
            question2_option_none.visibility = View.VISIBLE
            question2_next.visibility = View.GONE
        }
    }

    private fun updateAnswersQ2() {
        val answers = ArrayList<String>()
        if (question2_option1.isSelected) answers.add(question2_option1.text.toString())
        if (question2_option2.isSelected) answers.add(question2_option2.text.toString())
        if (question2_option3.isSelected) answers.add(question2_option3.text.toString())
        if (question2_option4.isSelected) answers.add(question2_option4.text.toString())
        if (question2_option5.isSelected) answers.add(question2_option5.text.toString())

        questionAnswerMap[2] = answers.joinToString()
        displayAnswer2()
    }

    private fun displayAnswer2() {
        group_q2_options.visibility = View.GONE
        question2_option_none.visibility = View.GONE
        question2_next.visibility = View.GONE
        question2_answer.text = questionAnswerMap[2]
        question2_answer.visibility = View.VISIBLE
        if (questionAnswerMap[1].equals(question1_option_none.text.toString())) {
            askQuestion3()
        } else {
            askQuestion5()
        }
    }

    private fun askQuestion3() {
        viewOfLayout.row_question3.visibility = View.VISIBLE
        scrollToEnd()
    }

    private fun question3Answered(answer: String) {
        group_q3_options.visibility = View.GONE
        questionAnswerMap[3] = answer
        question3_answer.text = answer
        question3_answer.visibility = View.VISIBLE
    }

    private fun askQuestion4() {
        viewOfLayout.row_question4.visibility = View.VISIBLE
        scrollToEnd()
    }

    private fun question4Answered(answer: String) {
        group_q4_options.visibility = View.GONE
        questionAnswerMap[4] = answer
        question4_answer.text = answer
        question4_answer.visibility = View.VISIBLE
    }

    private fun askQuestion5() {
        viewOfLayout.row_question5.visibility = View.VISIBLE
        scrollToEnd()
    }

    private fun question5Answered(answer: String) {
        group_q5_options.visibility = View.GONE
        questionAnswerMap[5] = answer
        question5_answer.text = answer
        question5_answer.visibility = View.VISIBLE
        showHighRisk()
    }

    private fun askQuestion6() {
        viewOfLayout.row_question6.visibility = View.VISIBLE
        scrollToEnd()
    }

    private fun question6Answered(answer: String) {
        group_q6_options.visibility = View.GONE
        questionAnswerMap[6] = answer
        question6_answer.text = answer
        question6_answer.visibility = View.VISIBLE
        showHighRisk()
    }

    private fun showLowRisk() {
        row_result_low_risk.visibility = View.VISIBLE
        scrollToEnd()
        finalAnswers()
    }

    private fun showHighRisk() {
        row_result_high_risk.visibility = View.VISIBLE
        scrollToEnd()
        finalAnswers()
    }

    private fun finalAnswers() {

        /*Sample Question-
        "Question: Are you experiencing Are you experiencing any of the following symptoms?" +
                "\nAnswer: Cough, Fever\nQuestion: Have you ever had any of the following?" +
                "\nAnswer: Hypertension, Heart Disease\nQuestion: Which of the following apply to you?" +
                "\nAnswer: Recently interacted or lived or currently living with someone who has
                tested positive for COVID 19\n"
        */

        val questions = HashMap<Int, String>()
        questions[1] = text_question1.text.toString()
        questions[2] = text_question2.text.toString()
        questions[3] = text_question3.text.toString()
        questions[4] = text_question4.text.toString()
        questions[5] = text_question5.text.toString()
        questions[6] = text_question6.text.toString()
        questionnaire = ""

        questionAnswerMap.forEach {
            Log.i("MAP value is --", it.key.toString() + " : " + it.value)
            questionnaire =
                questionnaire + "Question: " + questions[it.key] + "\nAnswer: " + it.value + "\n"
        }

        Log.i("questionnaire value is --", questionnaire)

        // todo: Remove in production build, just for testing values
        stringFinalTv.visibility = View.GONE
        //stringFinalTv.text = questionnaire
    }

    private fun highRiskLaterClicked() {
        closeFragment()
    }

    private fun highRiskConfirmClicked() {
        val dbPath = "self-assistance"

        val status = Status(
            App.prefs.userName,
            App.prefs.userEmpID,
            App.prefs.userMobileNumber,
            questionnaire,
            App.prefs.userEmailAddress,
            System.currentTimeMillis().toString()
        )

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference(dbPath)
        val key = myRef.push().key
        val statusValues = status.toMap()

        myRef.child(key!!).setValue(statusValues).addOnSuccessListener {
            Toast.makeText(context, "Status submitted!", Toast.LENGTH_SHORT).show()
            closeFragment()
        }.addOnFailureListener {
            Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun lowRiskOptionClicked() {
        closeFragment()
    }

    private fun closeFragment() {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(
            R.id.fragment_container,
            WelcomeFragment.newInstance(App.prefs.userName)
        )
        transaction?.addToBackStack(null)
        transaction?.commit()
        activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
    }

}