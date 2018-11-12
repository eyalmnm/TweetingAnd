package com.em_projects.tweetings.view.main.menu.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.RadioButton
import android.widget.RadioGroup
import com.em_projects.tweetings.R
import com.em_projects.tweetings.model.drawerModels.BenefitModel

// Ref: https://stackoverflow.com/questions/19163628/adding-custom-radio-buttons-in-android

class BenefitsFragment : Fragment() {
    private val TAG: String = "BenefitsFragment"

    // UI Components
    private lateinit var benefitsBusinessTypeRadioGroup: RadioGroup
    private lateinit var benefitsBrandsAndNetworksRadioButton: RadioButton
    private lateinit var benefitsLocalsBusinessRadioButton: RadioButton
    private lateinit var benefitsGridView: GridView

    // Benefits GridView components
    private lateinit var benefitsGridAdapter: BenefitsGridView
    private lateinit var benefitsArrayList: ArrayList<BenefitModel>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Creates the view controlled by the fragment
        return inflater.inflate(R.layout.fragment_benefits, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated")

        benefitsArrayList = ArrayList<BenefitModel>()
        for (i in 0..20) {  // TODO remove this init
            benefitsArrayList.add(BenefitModel())
        }

        // Bind and init the Business Type Radio Group's buttons
        benefitsBusinessTypeRadioGroup = view.findViewById(R.id.benefitsBusinessTypeRadioGroup)
        benefitsBrandsAndNetworksRadioButton = view.findViewById(R.id.benefitsBrandsAndNetworksRadioButton)
        benefitsLocalsBusinessRadioButton = view.findViewById(R.id.benefitsLocalsBusinessRadioButton)
        benefitsBusinessTypeRadioGroup.setOnCheckedChangeListener(
                RadioGroup.OnCheckedChangeListener { group, checkedId ->
                    val radio: RadioButton = view.findViewById(checkedId)
                    var text: String = "";
                    if (radio.id == R.id.benefitsBrandsAndNetworksRadioButton) text = "BrandsAnd Networks"
                    if (radio.id == R.id.benefitsLocalsBusinessRadioButton) text = "Local Business"
                    Log.d(TAG, " On checked change : $text")
                })

        // Bind and init the Grid View
        benefitsGridView = view.findViewById(R.id.benefitsGridView);
        benefitsGridView.setOnItemClickListener { parent, view, position, id ->
            //            Toast.makeText(this, "Clicked item : $position",Toast.LENGTH_SHORT).show()
//            Intent intent = new Intent(CurrentActivity.this, NextActivity::class.java)
//            intent.putExtra("position", position)
//            this.startActivity(intent)
        }

        benefitsGridAdapter = BenefitsGridView()
        benefitsGridView.adapter = benefitsGridAdapter
    }


    inner class BenefitsGridView : BaseAdapter() {

        override fun getItem(position: Int): Any {
            return benefitsArrayList.get(position)
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return benefitsArrayList.size
        }

        override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {
            var aView = view
            if (aView == null) {
                aView = LayoutInflater.from(context).inflate(R.layout.item_benefits_grid, null)
            }
            return aView!!;
        }
    }
}