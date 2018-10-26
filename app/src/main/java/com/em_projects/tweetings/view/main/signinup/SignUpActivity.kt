package com.em_projects.tweetings.view.main.signinup

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.*
import com.em_projects.tweetings.R
import com.em_projects.tweetings.config.Constants
import com.em_projects.tweetings.utils.StringUtils
import com.em_projects.tweetings.view.main.adapters.SpinnerAdapter
import kotlinx.android.synthetic.main.activity_signup.*
import java.text.SimpleDateFormat
import java.util.*

class SignUpActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private val TAG = "SignUpActivity"

    // Living Area's Spinner
    private var signUpAreasSpinner: Spinner? = null;
    private var adapter: SpinnerAdapter? = null
    private var livingAreas: ArrayList<String>? = null
    private var area: String = "center"

    // Date Picker Dialog
    private var cal = Calendar.getInstance()
    private var signUpJoinDateEditText: TextView? = null
    private var signUpJoinDateButton: ImageButton? = null
    private var date: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_signup)
        Log.d(TAG, "onCreate")

        // Init Spinner
        livingAreas = resources.getStringArray(R.array.living_areas_array).toCollection(ArrayList())
        adapter = SpinnerAdapter(this, R.layout.simple_spinner_item, livingAreas!!)
        signUpAreasSpinner = findViewById<Spinner>(R.id.signUpAreasSpinner)
        signUpAreasSpinner?.adapter = adapter // SpinnerAdapter(this, R.layout.simple_spinner_item, livingAreas!!)
        signUpAreasSpinner?.onItemSelectedListener = this
        area = livingAreas?.get(0)!!
        signUpAreasSpinner?.setSelection(0, true)

        signUpJoinDateEditText = findViewById(R.id.signUpJoinDateEditText)
        signUpJoinDateButton = findViewById(R.id.signUpJoinDateButton)
        // when you click on the button, show DatePickerDialog that is set with OnDateSetListener
        signUpJoinDateButton!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(this@SignUpActivity,
                        dateSetListener,
                        // set DatePickerDialog to point to today's date when it loads up
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)).show()
            }
        })

        closeButton.setOnClickListener { view ->
            onBackPressed()
        }

        link_login.setOnClickListener { view ->
            openLoginScreen()
        }

        btn_signup.setOnClickListener { view ->
            sendSignUpData()
        }
    }

    private fun sendSignUpData() {
        val name = signUpNameEditText.text.toString()
        val phone: String = signUpPhoneEditText.text.toString()
        val email: String = signUpEmailEditText.text.toString()
        val joinDate: Long = date
        val livingArea: String = area
        val password: String = input_password.text.toString()
        val rePassword: String = input_reEnterPassword.text.toString()
        val acceptEula: Boolean = signUpAcceptEula.isChecked
        val acceptOffer: Boolean = signUpAcceptOffers.isChecked
        if (StringUtils.isNullOrEmpty(name)) {
            Toast.makeText(this, R.string.missing_name, Toast.LENGTH_LONG).show()
            return
        }
        if (StringUtils.isNullOrEmpty(phone)) {
            Toast.makeText(this, R.string.missing_phone, Toast.LENGTH_LONG).show()
            return
        }
        if (StringUtils.isNullOrEmpty(email)) {
            Toast.makeText(this, R.string.missing_email, Toast.LENGTH_LONG).show()
            return
        }
        if (joinDate <= 0) {
            Toast.makeText(this, R.string.missing_join_date, Toast.LENGTH_LONG).show()
            return
        }
        if (StringUtils.isNullOrEmpty(livingArea)) {
            Toast.makeText(this, R.string.missing_living_area, Toast.LENGTH_LONG).show()
            return
        }
        if (StringUtils.isNullOrEmpty(password)) {
            Toast.makeText(this, R.string.missing_password, Toast.LENGTH_LONG).show()
            return
        }
        if (!password.equals(rePassword)) {
            Toast.makeText(this, R.string.password_mismatch, Toast.LENGTH_LONG).show()
            return
        }
        var intent: Intent = Intent()
        intent.putExtra(Constants.NAME, name)
        intent.putExtra(Constants.PHONE, phone)
        intent.putExtra(Constants.EMAIL, email)
        intent.putExtra(Constants.JOIN_DATE, joinDate)
        intent.putExtra(Constants.LIVING_AREA, livingArea)
        intent.putExtra(Constants.PASSWORD, password)
        intent.putExtra(Constants.ACCEPT_EULA, acceptEula)
        intent.putExtra(Constants.ACCEPT_OFFER, acceptOffer)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun openLoginScreen() {
        var intent: Intent = Intent(Constants.ACTION_SHOW_SIGN_IN_DIALOG)
        setResult(Activity.RESULT_CANCELED, intent)
        finish()
    }

    // OnItemSelectedListener implementation method
    override fun onNothingSelected(p0: AdapterView<*>?) {
    }

    // OnItemSelectedListener implementation method
    override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, p3: Long) {
        area = livingAreas?.get(position)!!
    }

    val dateSetListener = object : DatePickerDialog.OnDateSetListener {
        override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                               dayOfMonth: Int) {
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            date = cal.timeInMillis
            updateDateInView()
        }
    }

    private fun updateDateInView() {
        val myFormat = "MM/dd/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        signUpJoinDateEditText!!.text = sdf.format(cal.getTime())
    }

    /**
     * Take care of popping the fragment back stack or finishing the activity
     * as appropriate.
     */
    override fun onBackPressed() {
        var intent: Intent = Intent(Constants.ACTION_OPERATION_CANCELLED)
        setResult(Activity.RESULT_CANCELED, intent)
        super.onBackPressed()
    }
}