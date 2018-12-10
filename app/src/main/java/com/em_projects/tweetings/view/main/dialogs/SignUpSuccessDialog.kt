package com.em_projects.tweetings.view.main.dialogs

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import com.em_projects.tweetings.R

class SignUpSuccessDialog : DialogFragment(), View.OnClickListener {
    private val TAG: String = "SignUpSuccessDialog"

    // UI Components
    private lateinit var mainImageView: ImageView
    private lateinit var okButton: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG, "onCreateView")
        val view = inflater.inflate(R.layout.dialog_one_button_container, container, true)
        dialog.requestWindowFeature(DialogFragment.STYLE_NO_TITLE)
        dialog.setCancelable(true)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewCreated")

        mainImageView = view.findViewById(R.id.mainImageView)
        mainImageView.setImageDrawable(resources.getDrawable(R.drawable.layer_4))

        okButton = view.findViewById(R.id.okButton)
        okButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        dismiss()
    }
}