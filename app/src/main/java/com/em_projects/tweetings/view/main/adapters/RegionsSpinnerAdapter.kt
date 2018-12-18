package com.em_projects.tweetings.view.main.adapters

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.em_projects.tweetings.R
import com.em_projects.tweetings.model.RegionModel

class RegionsSpinnerAdapter(private val activity: AppCompatActivity, private val itextViewResourceId: Int, private val regions: ArrayList<RegionModel>) :
        ArrayAdapter<RegionModel>(activity, itextViewResourceId, regions) {

    private var inflater: LayoutInflater? = null

    init {
        inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return getCustomView(position, convertView, parent)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return getCustomView(position, convertView, parent)
    }

    private fun getCustomView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var row = convertView
        if (row == null) {
            row = inflater?.inflate(itextViewResourceId, parent, false)
        }
        val textView: TextView = row?.findViewById(R.id.datTextView)!!
        textView.gravity = Gravity.END
        textView.setBackgroundColor(context.resources.getColor(android.R.color.transparent))
        textView.text = regions.get(position).title
        return row
    }
}