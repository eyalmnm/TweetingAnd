package com.em_projects.tweetings.view.main.dialogs

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.em_projects.tweetings.R

class ForgetPasswordDialog : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        isCancelable = true
        return inflater.inflate(R.layout.dialog_forget_password, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var closeButton = view.findViewById<ImageView>(R.id.closeButton) as ImageView
        closeButton.setOnClickListener { view ->
            dismiss();
        }
    }
}