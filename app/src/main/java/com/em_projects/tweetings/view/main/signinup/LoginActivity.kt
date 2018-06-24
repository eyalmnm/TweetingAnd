package com.em_projects.tweetings.view.main.signinup

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Window
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.em_projects.tweetings.R
import com.em_projects.tweetings.config.Constants
import com.em_projects.tweetings.utils.StringUtils
import kotlinx.android.synthetic.main.activity_login.*

// Ref: https://stackoverflow.com/questions/37201504/how-to-setoneditoractionlistener-with-kotlin
class LoginActivity : AppCompatActivity() {
    private val TAG: String = "LoginActivity";

    private var context: Context? = null
    private var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        //FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_login)
        Log.d(TAG, "onCreate")

        loginPasswordEditText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                btn_login.performClick()
                true
            } else {
                false
            }
        }

        btn_login.setOnClickListener { view ->
            performLogin()
        }

        facebook_login_button.setOnClickListener { view ->
            loginWithFacebook()
        }

        loginForgetPassword.setOnClickListener { view ->
            showForgetPasswordDialog()
        }

        link_signup.setOnClickListener { view ->
            openSignUpDialog()
        }

        closeButton.setOnClickListener { view ->
            onBackPressed()
        }
    }

    private fun openSignUpDialog() {
        var intent = Intent(Constants.ACTION_SHOW_SIGN_UP_DIALOG)
        setResult(Activity.RESULT_CANCELED, intent)
        finish()
    }

    private fun showForgetPasswordDialog() {
        setResult(Activity.RESULT_CANCELED, Intent(Constants.ACTION_SHOW_FORGET_PASSWORD_DIALOG))
        finish()
    }

    private fun loginWithFacebook() {
        TODO("not implemented")
    }

    private fun performLogin() {
        var email = loginEmailEditText.text.toString()
        var password = loginPasswordEditText.text.toString()

        if (StringUtils.isNullOrEmpty(email) || StringUtils.isNullOrEmpty(password)) {
            Toast.makeText(context, R.string.missing_data_all, Toast.LENGTH_SHORT).show()
        } else {

        }
    }

    override fun onBackPressed() {
        var intent: Intent = Intent(Constants.ACTION_OPERATION_CANCELLED)
        setResult(Activity.RESULT_CANCELED, intent)
    }
}