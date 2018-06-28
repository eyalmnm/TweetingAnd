package com.em_projects.tweetings.view.main.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.em_projects.tweetings.model.drawerModels.OpeningOfferModel
import com.em_projects.tweetings.view.main.menu.fragments.OfferFragment

// Ref: https://www.raywenderlich.com/169774/viewpager-tutorial-android-getting-started-kotlin

class OffersPagerAdapter(fragmentManager: FragmentManager, private val offers: ArrayList<OpeningOfferModel>) :
        FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return OfferFragment.newInstance(offers[position])
    }

    override fun getCount(): Int {
        return offers.size
    }
}