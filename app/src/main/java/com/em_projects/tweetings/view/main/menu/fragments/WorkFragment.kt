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

class WorkFragment : Fragment(), AdapterView.OnItemSelectedListener {
    private val TAG: String = "WorkFragment"

    // Work Area Spinner
    lateinit var workAreaSpinner: Spinner
    private var workAreaAdapter: SpinnerAdapter? = null
    private var workAreaList: ArrayList<String>? = null
    lateinit var area: String

    // Work Domain Spinner
    lateinit var worDomainSpinner: Spinner
    private var workDomeinAdapter: SpinnerAdapter? = null
    private var workDomainList: ArrayList<String>? = null
    lateinit var domain: String

    // Citizenship Spinner
    lateinit var worCitizenshipSpinner: Spinner
    private var citizenshipAdapter: SpinnerAdapter? = null
    private var citizenshipList: ArrayList<String>? = null
    lateinit var citizenship: String

    // Working Visa Spinner
    lateinit var worVisaSpinner: Spinner
    private var workingVisaAdapter: SpinnerAdapter? = null
    private var workingVisaList: ArrayList<String>? = null
    lateinit var workingVisa: String


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_works, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewCreated")

        // Init Work Area Spinner
        workAreaSpinner = view.findViewById(R.id.workAreaSpinner)
        workAreaList = resources.getStringArray(R.array.living_areas_array).toCollection(ArrayList())
        workAreaAdapter = SpinnerAdapter((activity as AppCompatActivity), R.layout.simple_spinner_item, workAreaList!!)
        workAreaSpinner.adapter = workAreaAdapter
        workAreaSpinner.onItemSelectedListener = this
        area = workAreaList!!.get(0)
        workAreaSpinner.setSelection(0)

        // Init Working Domain Spinner
        worDomainSpinner = view.findViewById(R.id.worDomainSpinner)
        workDomainList = resources.getStringArray(R.array.work_domain).toCollection(ArrayList())
        workDomeinAdapter = SpinnerAdapter((activity as AppCompatActivity), R.layout.simple_spinner_item, workDomainList!!)
        worDomainSpinner.adapter = workDomeinAdapter
        worDomainSpinner.onItemSelectedListener = this
        domain = workDomainList!!.get(0)
        worDomainSpinner.setSelection(0)

        // Init Citizenship Spinner
        worCitizenshipSpinner = view.findViewById(R.id.worCitizenshipSpinner)
        citizenshipList = resources.getStringArray(R.array.citizenship_array).toCollection(ArrayList())
        citizenshipAdapter = SpinnerAdapter((activity as AppCompatActivity), R.layout.simple_spinner_item, citizenshipList!!)
        worCitizenshipSpinner.adapter = citizenshipAdapter
        worCitizenshipSpinner.onItemSelectedListener = this
        citizenship = citizenshipList!!.get(0)
        worCitizenshipSpinner.setSelection(0)

        worVisaSpinner = view.findViewById(R.id.worVisaSpinner)
        workingVisaList = resources.getStringArray(R.array.visa_array).toCollection(ArrayList())
        workingVisaAdapter = SpinnerAdapter((activity as AppCompatActivity), R.layout.simple_spinner_item, workingVisaList!!)
        worVisaSpinner.adapter = workingVisaAdapter
        worVisaSpinner.onItemSelectedListener = this
        workingVisa = workingVisaList!!.get(0)
        worVisaSpinner.setSelection(0)
    }

    // AdapterView.OnItemSelectedListener implementation
    override fun onNothingSelected(parent: AdapterView<*>?) {
        // TODO
    }

    // AdapterView.OnItemSelectedListener implementation
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Log.d(TAG, "parent: $parent view: $view position: $position id: $id")
        // TODO
    }
}