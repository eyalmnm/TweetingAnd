package com.em_projects.tweetings.view.main.signinup

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import android.widget.Toast
import com.em_projects.tweetings.R
import com.em_projects.tweetings.config.Constants
import com.em_projects.tweetings.utils.StringUtils
import kotlinx.android.synthetic.main.activity_forget_password.*

class ForgetPwdActivity : AppCompatActivity() {

    //@BindView(R.id.pwdEmailEditText)
    var email: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)

        pwdSendButton.setOnClickListener { view ->
            submitEmail()
        }

        forget_password_login_link.setOnClickListener { view ->
            returnToLoginScreen()
        }

        closeButton.setOnClickListener { view ->
            closeScreen()
        }
    }

    fun submitEmail() {
        val emailStr = pwdEmailEditText.text.toString()
        if (false == StringUtils.isNullOrEmpty(emailStr)) {
            var intent: Intent = Intent()
            intent.putExtra(Constants.EMAIL, emailStr)
            setResult(Activity.RESULT_OK, intent)
            finish()
        } else {
            Toast.makeText(this, R.string.missing_email, Toast.LENGTH_LONG).show()
        }
    }

    fun returnToLoginScreen() {
        var intent: Intent = Intent(Constants.ACTION_SHOW_SIGN_IN_DIALOG)
        setResult(Activity.RESULT_CANCELED, intent)
        finish()
    }

    fun closeScreen() {
        var intent: Intent = Intent(Constants.ACTION_SHOW_SIGN_IN_DIALOG)
        setResult(Activity.RESULT_CANCELED, intent)
        finish()
    }
}