package com.em_projects.tweetings.view.main.studies

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
import com.em_projects.tweetings.R
import com.em_projects.tweetings.config.Constants
import com.em_projects.tweetings.config.Dynamic
import com.em_projects.tweetings.model.RegionModel
import com.em_projects.tweetings.utils.StringUtils
import com.em_projects.tweetings.view.main.adapters.RegionsSpinnerAdapter
import com.em_projects.tweetings.view.main.adapters.SpinnerAdapter
import com.em_projects.tweetings.view.main.studies.data_types.DummyChildDataItem
import com.em_projects.tweetings.view.main.studies.data_types.DummyParentDataItem
import kotlinx.android.synthetic.main.activity_studies_contact_me.*

class StudiesContactMeActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private val TAG: String = "StudiesContactMeActivity"

    // Living Area's Spinner
    private var studiesAreasSpinner: Spinner? = null
    private var adapter: RegionsSpinnerAdapter? = null
    private var livingAreas: ArrayList<RegionModel>? = null
    private var area: RegionModel? = null

    // Bagrut's Spinner
    private var studiesCertificationSpinner: Spinner? = null
    private var bagrutAdapter: SpinnerAdapter? = null
    private var bagrutTypes: ArrayList<String>? = null
    private var bagrut: String = "Type A"

    //


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_studies_contact_me)

        // Init Living Area Spinner
        livingAreas = Dynamic.regionsModel!!.regions.toCollection(ArrayList())
        adapter = RegionsSpinnerAdapter(this, R.layout.simple_spinner_item, livingAreas!!)
        studiesAreasSpinner = findViewById(R.id.studiesAreasSpinner)
        studiesAreasSpinner?.adapter = adapter
        studiesAreasSpinner?.onItemSelectedListener = this
        area = livingAreas?.get(0)!!
        studiesAreasSpinner?.setSelection(0, true)

        // Init Bagrut Spinner
        bagrutTypes = resources.getStringArray(R.array.bagrot_array).toCollection(ArrayList())
        bagrutAdapter = SpinnerAdapter(this, R.layout.simple_spinner_item, bagrutTypes!!)
        studiesCertificationSpinner = findViewById(R.id.studiesCertificationSpinner)
        studiesCertificationSpinner?.adapter = bagrutAdapter
        studiesCertificationSpinner?.onItemSelectedListener = this
        bagrut = bagrutTypes?.get(0)!!
        studiesCertificationSpinner?.setSelection(0, true)

        btn_callMeStudies.setOnClickListener { view ->
            sendCallMeData()
        }
    }

    private fun sendCallMeData() {
        val name: String = worksNameEditText.text.toString()
        val phone: String = studiesPhoneEditText.text.toString()
        val domain: String = worksPhoneEditText.text.toString()
        val acceptEula: Boolean = studiesAcceptEula.isChecked
        val acceptOffer: Boolean = studiesAcceptOffers.isChecked

        if (StringUtils.isNullOrEmpty(name)) {
            Toast.makeText(this, R.string.missing_name, Toast.LENGTH_LONG).show()
            return
        }
        if (StringUtils.isNullOrEmpty(phone)) {
            Toast.makeText(this, R.string.missing_phone, Toast.LENGTH_LONG).show()
            return
        }
        if (StringUtils.isNullOrEmpty(domain)) {
            Toast.makeText(this, R.string.missing_domain, Toast.LENGTH_LONG).show()
            return
        }

        var intent: Intent = Intent()
        intent.putExtra(Constants.NAME, name)
        intent.putExtra(Constants.PHONE, phone)
        intent.putExtra(Constants.DOMAIN, domain)
        intent.putExtra(Constants.LIVING_AREA, area)
        intent.putExtra(Constants.BAGRUT, bagrut)
        intent.putExtra(Constants.ACCEPT_EULA, acceptEula)
        intent.putExtra(Constants.ACCEPT_OFFER, acceptOffer)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    // AdapterView.OnItemSelectedListener implementation
    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    // AdapterView.OnItemSelectedListener implementation
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (parent == studiesAreasSpinner) {
            area = livingAreas?.get(position)!!
        } else if (parent == studiesCertificationSpinner) {
            bagrut = bagrutTypes?.get(position)!!
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


    // Dummi Data Creator
    private fun getDummyDataToPass(): ArrayList<DummyParentDataItem> {
        val dummyDataItems = ArrayList<DummyParentDataItem>()
        var dummyChildDataItems: ArrayList<DummyChildDataItem>
        var dummyParentDataItem: DummyParentDataItem
        var dummyChildDataItem: DummyChildDataItem
        /////////
        dummyParentDataItem = DummyParentDataItem()
        dummyParentDataItem.setParentName("Parent 1")
        dummyChildDataItems = ArrayList()
        //
        dummyChildDataItem = DummyChildDataItem()
        dummyChildDataItem.setChildName("Child Item 1")
        dummyChildDataItems.add(dummyChildDataItem)
        //
        dummyParentDataItem.setChildDataItems(dummyChildDataItems)
        dummyDataItems.add(dummyParentDataItem)
        ////////
        dummyParentDataItem = DummyParentDataItem()
        dummyParentDataItem.setParentName("Parent 2")
        dummyChildDataItems = ArrayList()
        //
        dummyChildDataItem = DummyChildDataItem()
        dummyChildDataItem.setChildName("Child Item 1")
        dummyChildDataItems.add(dummyChildDataItem)
        //
        dummyChildDataItem = DummyChildDataItem()
        dummyChildDataItem.setChildName("Child Item 2")
        dummyChildDataItems.add(dummyChildDataItem)
        //
        dummyParentDataItem.setChildDataItems(dummyChildDataItems)
        dummyDataItems.add(dummyParentDataItem)
        ////////
        dummyParentDataItem = DummyParentDataItem()
        dummyParentDataItem.setParentName("Parent 3")
        dummyChildDataItems = ArrayList()
        //
        dummyChildDataItem = DummyChildDataItem()
        dummyChildDataItem.setChildName("Child Item 1")
        dummyChildDataItems.add(dummyChildDataItem)
        //
        dummyChildDataItem = DummyChildDataItem()
        dummyChildDataItem.setChildName("Child Item 2")
        dummyChildDataItems.add(dummyChildDataItem)
        //
        dummyChildDataItem = DummyChildDataItem()
        dummyChildDataItem.setChildName("Child Item 3")
        dummyChildDataItems.add(dummyChildDataItem)
        //
        dummyChildDataItem = DummyChildDataItem()
        dummyChildDataItem.setChildName("Child Item 4")
        dummyChildDataItems.add(dummyChildDataItem)
        //
        dummyChildDataItem = DummyChildDataItem()
        dummyChildDataItem.setChildName("Child Item 5")
        dummyChildDataItems.add(dummyChildDataItem)
        //
        dummyParentDataItem.setChildDataItems(dummyChildDataItems)
        dummyDataItems.add(dummyParentDataItem)
        ////////
        return dummyDataItems
    }
}