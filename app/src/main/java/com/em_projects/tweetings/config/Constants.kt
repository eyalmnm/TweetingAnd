package com.em_projects.tweetings.config

import com.em_projects.tweetings.BuildConfig

class Constants {
    companion object {

        val TWEET_SERVER_URL = BuildConfig.SERVER_URL

        // General Actions
        val ACTION_OPERATION_CANCELLED = "action_operation_cancelled"

        // Sign In / Sign Up Actions
        val ACTION_SHOW_SIGN_UP_DIALOG = "action_show_sign_up_dialog"
        val ACTION_SHOW_SIGN_IN_DIALOG = "action_show_sign_in_dialog"
        val ACTION_SHOW_FORGET_PASSWORD_DIALOG = "action_show_forget_password_dialog"

        // Sign In
        val SIGN_IN_DATA_EMAIL = "email"
        val SIGN_IN_DATA_PASSWORD = "password"

        // API
        val CITIZEN = "CITIZEN"
        val VISA = "VISA"
        val ACCEPT_EULA = "ACCEPT_EULA"
        val ACCEPT_OFFER = "ACCEPT_OFFER"
        val NAME = "NAME"
        val PHONE = "PHONE"
        val AGE = "AGE"
        val EMAIL = "EMAIL"
        val JOIN_DATE = "JOIN_DATE"
        val LIVING_AREA = "LIVING_AREA"
        val PASSWORD = "PASSWORD"
    }
}