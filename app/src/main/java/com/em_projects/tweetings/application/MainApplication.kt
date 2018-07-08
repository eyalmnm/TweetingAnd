package com.em_projects.tweetings.application

import android.app.Application
import com.em_projects.tweetings.utils.TypefaceUtil

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // font from assets: "assets/fonts/Roboto-Regular.ttf
//        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/Roboto-Regular.ttf")

        // font from assets: "assets/fonts/rubik_regular.otf
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/rubik_regular.otf")
    }
}