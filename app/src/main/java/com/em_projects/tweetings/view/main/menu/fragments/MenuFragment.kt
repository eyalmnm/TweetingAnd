package com.em_projects.tweetings.view.main.menu.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.em_projects.tweetings.R
import com.em_projects.tweetings.config.Dynamic


class MenuFragment : Fragment(), View.OnClickListener {
    private var TAG: String = "MenuFragment"

    interface OnMenuItemClickedListener {
        fun onMenuMenuItemClicked(itemId: Int)
    }

    private var listener: OnMenuItemClickedListener? = null

    var gestureDetector: GestureDetector? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        listener = context as OnMenuItemClickedListener
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val root = inflater.inflate(R.layout.layout_main_menu, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (Dynamic.uuid != null) {
            // TODO show user exist buttons
        } else {
            // TODO show new user buttons
        }
        // Bind all "items" and buttons
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    override fun onClick(v: View?) {
        listener!!.onMenuMenuItemClicked(v!!.id)
    }

}