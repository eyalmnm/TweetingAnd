package com.em_projects.tweetings.view.main.signinup.adapters

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.em_projects.tweetings.R

class SpinnerAdapter(private val activity: AppCompatActivity, private val itextViewResourceId: Int, private val strings: ArrayList<String>) :
        ArrayAdapter<String>(activity, itextViewResourceId, strings) {

    private var inflater: LayoutInflater? = null

    init {
        inflater = activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return getCustomView(position, convertView, parent)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return getCustomView(position, convertView, parent)
    }

    private fun getCustomView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var row: View? = inflater?.inflate(itextViewResourceId, parent, false);
        var textView: TextView = row?.findViewById(R.id.datTextView)!!
        textView.text = strings.get(position)
        return row
    }
}