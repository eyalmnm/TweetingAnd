package com.em_projects.tweetings.view.main.signinup

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import com.em_projects.tweetings.R
import com.em_projects.tweetings.view.main.adapters.SpinnerAdapter

class SignUpActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private val TAG = "SignUpActivity"

    // Living Area's Spinner
    private var signUpAreasSpinner: Spinner? = null;
    private var adapter: SpinnerAdapter? = null
    private var livingAreas: ArrayList<String>? = null
    private var area: String = "center"

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
    }


    // OnItemSelectedListener implementation method
    override fun onNothingSelected(p0: AdapterView<*>?) {
    }

    // OnItemSelectedListener implementation method
    override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, p3: Long) {
        area = livingAreas?.get(position)!!
    }
}