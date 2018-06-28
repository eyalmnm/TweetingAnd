package com.em_projects.tweetings.model.drawerModels

class OpeningOfferModel(offerImageUrl: String, offerWebSiteUrl: String) {
    private var imageUrl: String? = null
    private var webUrl: String? = null

    init {
        imageUrl = offerImageUrl
        webUrl = offerWebSiteUrl
    }

    fun getImageUrl(): String? {
        return imageUrl
    }

    fun getWebUrl(): String? {
        return webUrl
    }
}