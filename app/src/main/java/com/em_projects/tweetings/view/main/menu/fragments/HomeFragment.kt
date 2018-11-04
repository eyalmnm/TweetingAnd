package com.em_projects.tweetings.view.main.menu.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.em_projects.tweetings.R
import com.em_projects.tweetings.model.drawerModels.OpeningOfferModel
import com.em_projects.tweetings.view.main.menu.OfferHelper

class HomeFragment : Fragment() {
    private val TAG: String = "HomeFragment"

    // UI Components
    lateinit var homeBenefitsLayout: LinearLayout
    lateinit var homeStudiesLayout: LinearLayout
    lateinit var homeWorksLayout: LinearLayout
    lateinit var homeFacebookLinkImageView: ImageView
    lateinit var homeInstegramLinkImageView: ImageView
    lateinit var homeYouTubeLinkImageView: ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState:
    Bundle?): View? {
        // Creates the view controlled by the fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var offerImageView = view.findViewById<ImageView>(R.id.homeImageView)

        // Retrieve and display the movie data from the Bundle
        val args = arguments
        if (args != null) {
            var imageUrl: String? = args!!.get(OfferHelper.KEY_IMAGE_URI) as String?

            // Download the image and display it using Picasso
            Glide.with(activity!!)
                    .load(imageUrl)
                    .into(offerImageView)
        }

    }

    companion object {
        // Method for creating new instances of the fragment
        fun newInstance(offer: OpeningOfferModel): HomeFragment {

            // Store the movie data in a Bundle object
            val args = Bundle()
            args.putString(OfferHelper.KEY_IMAGE_URI, offer.getImageUrl())

            // Create a new MovieFragment and set the Bundle as the arguments
            // to be retrieved and displayed when the view is created
            val fragment = HomeFragment()
            fragment.arguments = args
            return fragment
        }
    }
}