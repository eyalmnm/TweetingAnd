package com.em_projects.tweetings.view.main

import android.app.Activity
import android.app.FragmentManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.em_projects.tweetings.R
import com.em_projects.tweetings.config.Constants
import com.em_projects.tweetings.config.Dynamic
import com.em_projects.tweetings.utils.StringUtils
import com.em_projects.tweetings.view.main.dialogs.AppExitDialog
import com.em_projects.tweetings.view.main.signinup.LoginActivity
import com.em_projects.tweetings.view.main.signinup.SignUpActivity
import com.em_projects.tweetings.viewmodel.signinup.SignInViewModle

// Ref: https://android--code.blogspot.com/2018/03/android-kotlin-navigation-drawer-example.html

class MainScreenActivity : AppCompatActivity() {
    private val TAG: String = "MainScreenActivity";

    private val SHOW_LOGIN_ACTIVITY: Int = 123
    private val SHOW_SIGN_UP_ACTIVITY = SHOW_LOGIN_ACTIVITY + 1

    private var context: Context? = null

    private var signInViewModel: SignInViewModle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)
        Log.d(TAG, "onCreate")
        signInViewModel = SignInViewModle(this)
        context = this;

        continueLoading()
    }

    private fun continueLoading() {
        if (StringUtils.isNullOrEmpty(Dynamic.uuid)) {
            var intent = Intent(context, LoginActivity::class.java)
            startActivityForResult(intent, SHOW_LOGIN_ACTIVITY)
        }
    }

    /**
     * Dispatch incoming result to the correct fragment.
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == SHOW_LOGIN_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                var email: String? = data?.getStringExtra(Constants.SIGN_IN_DATA_EMAIL)
                var password: String? = data?.getStringExtra(Constants.SIGN_IN_DATA_PASSWORD)
                signInViewModel?.login(email, password)
            } else if (resultCode == Activity.RESULT_CANCELED) {
                if (data?.action.equals(Constants.ACTION_SHOW_SIGN_UP_DIALOG)) {
                    var intent = Intent(context, SignUpActivity::class.java)
                    startActivityForResult(intent, SHOW_SIGN_UP_ACTIVITY)
                } else if (data?.action.equals(Constants.ACTION_OPERATION_CANCELLED)) {
                    showExitDialog()
                }
            }
        } else if (requestCode == SHOW_SIGN_UP_ACTIVITY) {
            TODO("not implemented")
        }
    }

    private fun showExitDialog() {
        var fragmentManager: FragmentManager = getFragmentManager()
        var appExitDialog: AppExitDialog = AppExitDialog()
        appExitDialog.show(fragmentManager, "AppExitDialog")
    }

    /**
     * Take care of popping the fragment back stack or finishing the activity
     * as appropriate.
     */
    override fun onBackPressed() {
        showExitDialog()
    }
}