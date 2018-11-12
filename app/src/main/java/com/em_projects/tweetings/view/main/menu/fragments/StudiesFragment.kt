package com.em_projects.tweetings.view.main.menu.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import com.em_projects.tweetings.R
import com.em_projects.tweetings.view.main.adapters.SpinnerAdapter

class StudiesFragment : Fragment(), AdapterView.OnItemSelectedListener {
    private val TAG: String = "StudiesFragment";

    // Bagrut's Spinner
    lateinit var studiesCertificationsSpinner: Spinner
    private var bagrutAdapter: SpinnerAdapter? = null
    private var bagrutTypes: ArrayList<String>? = null
    private var bagrut: String = "Type A"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_studies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewCreated")

        studiesCertificationsSpinner = view.findViewById(R.id.studiesCertificationsSpinner)
        // Init Bagrut Spinner
        bagrutTypes = resources.getStringArray(R.array.bagrot_array).toCollection(ArrayList())
        bagrutAdapter = SpinnerAdapter((activity as AppCompatActivity), R.layout.simple_spinner_item, bagrutTypes!!)
        studiesCertificationsSpinner.adapter = bagrutAdapter
        studiesCertificationsSpinner.onItemSelectedListener = this
        bagrut = bagrutTypes?.get(0)!!
        studiesCertificationsSpinner.setSelection(0, true)
    }

    // AdapterView.OnItemSelectedListener implementation
    override fun onNothingSelected(parent: AdapterView<*>?) {
        // TODO
    }

    // AdapterView.OnItemSelectedListener implementation
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        // TODO
    }

}