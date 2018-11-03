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

class OfferFragment : Fragment() {
    private val TAG: String = "OfferFragment"

    // UI Components
    lateinit var offerBenefitsLayout: LinearLayout
    lateinit var offerStudiesLayout: LinearLayout
    lateinit var offerWorksLayout: LinearLayout
    lateinit var offerFacebookLinkImageView: ImageView
    lateinit var offerInstegramLinkImageView: ImageView
    lateinit var offerYouTubeLinkImageView: ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState:
    Bundle?): View? {

        // Creates the view controlled by the fragment
        val view = inflater.inflate(R.layout.fragment_offer, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var offerImageView = view.findViewById<ImageView>(R.id.offerImageView)

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
        fun newInstance(offer: OpeningOfferModel): OfferFragment {

            // Store the movie data in a Bundle object
            val args = Bundle()
            args.putString(OfferHelper.KEY_IMAGE_URI, offer.getImageUrl())

            // Create a new MovieFragment and set the Bundle as the arguments
            // to be retrieved and displayed when the view is created
            val fragment = OfferFragment()
            fragment.arguments = args
            return fragment
        }
    }
}