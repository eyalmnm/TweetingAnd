package com.em_projects.tweetings.view.main.jobs

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import com.em_projects.tweetings.R
import com.em_projects.tweetings.config.Constants
import com.em_projects.tweetings.view.main.adapters.SpinnerAdapter
import kotlinx.android.synthetic.main.activity_send_my_details.*

// Ref: https://tutorialwing.com/android-spinner-using-kotlin-with-example/

class SendMyDetailsActivity : AppCompatActivity() {
    private val TAG = "SendMyDetailsActivity"

    // Citizen's Spinner
    private var sendMyDetailsCitizenshipSpinner: Spinner? = null;
    private var citizensAdapter: SpinnerAdapter? = null
    private var citizenshipsList: ArrayList<String>? = null
    private var citizen: String = "center"

    // Visa's Spinner
    private var sendMyDetailsVisaSpinner: Spinner? = null;
    private var visaAdapter: SpinnerAdapter? = null
    private var visaList: ArrayList<String>? = null
    private var visa: String = "center"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_my_details)
        Log.d(TAG, "onCreate")

        // Init Citizenship Spinner
        citizenshipsList = resources.getStringArray(R.array.citizenship_array).toCollection(ArrayList())
        citizensAdapter = SpinnerAdapter(this, R.layout.simple_spinner_item, citizenshipsList!!)
        sendMyDetailsCitizenshipSpinner = findViewById(R.id.sendMyDetailsCitizenshipSpinner)
        sendMyDetailsCitizenshipSpinner?.adapter = citizensAdapter
        sendMyDetailsCitizenshipSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                citizen = citizenshipsList?.get(position)!!
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
        citizen = citizenshipsList?.get(0)!!
        sendMyDetailsCitizenshipSpinner?.setSelection(0, true)


        // Init Visa Spinner
        visaList = resources.getStringArray(R.array.visa_array).toCollection(ArrayList())
        visaAdapter = SpinnerAdapter(this, R.layout.simple_spinner_item, visaList!!)
        sendMyDetailsVisaSpinner = findViewById(R.id.sendMyDetailsVisaSpinner)
        sendMyDetailsVisaSpinner?.adapter = visaAdapter
        sendMyDetailsVisaSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                visa = visaList?.get(position)!!
            }
        }
        visa = visaList?.get(0)!!
        sendMyDetailsVisaSpinner?.setSelection(0, true)

        closeButton.setOnClickListener { view ->
            onBackPressed()
        }

        sendMyDetailsCallMeButton.setOnClickListener { view ->
            var name: String = sendMyDetailsNameEditText.text.toString()
            var phone: String = sendMyDetailsPhoneEditText.text.toString()
            var age: String = sendMyDetailsAgeEditText.text.toString()
            var acceptEula: Boolean = sendMyDetailsAcceptEula.isChecked
            var acceptOffer: Boolean = sendMyDetailsAcceptOffers.isChecked
            var intent: Intent = Intent()
            intent.putExtra(Constants.CITIZEN, citizen)
            intent.putExtra(Constants.VISA, visa)
            intent.putExtra(Constants.ACCEPT_EULA, acceptEula)
            intent.putExtra(Constants.ACCEPT_OFFER, acceptOffer)
            intent.putExtra(Constants.NAME, name)
            intent.putExtra(Constants.PHONE, phone)
            intent.putExtra(Constants.AGE, age)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
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