package com.em_projects.tweetings.view.main

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.em_projects.tweetings.R
import com.em_projects.tweetings.config.Constants
import com.em_projects.tweetings.config.Dynamic
import com.em_projects.tweetings.model.DataWrapper
import com.em_projects.tweetings.model.RegionModel
import com.em_projects.tweetings.utils.StringUtils
import com.em_projects.tweetings.view.main.dialogs.*
import com.em_projects.tweetings.view.main.signinup.ForgetPwdActivity
import com.em_projects.tweetings.view.main.signinup.LoginActivity
import com.em_projects.tweetings.view.main.signinup.SignUpActivity
import com.em_projects.tweetings.viewmodel.signinup.SignInViewModel

// Ref: https://android--code.blogspot.com/2018/03/android-kotlin-navigation-drawer-example.html

class MainScreenActivity : AppCompatActivity() {
    private val TAG: String = "MainScreenActivity"

    private val SHOW_LOGIN_ACTIVITY: Int = 123
    private val SHOW_SIGN_UP_ACTIVITY = SHOW_LOGIN_ACTIVITY + 1
    private val SHOW_FORGET_PASSWORD_ACTIVITY = SHOW_SIGN_UP_ACTIVITY + 1

    private lateinit var context: Context
    private lateinit var fragmentManager: FragmentManager

    private var signInViewModel: SignInViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)
        Log.d(TAG, "onCreate")
        signInViewModel = ViewModelProviders.of(this).get(SignInViewModel::class.java)
        context = this
        fragmentManager = supportFragmentManager

        if (StringUtils.isNullOrEmpty(Dynamic.uuid)) {
            val intent = Intent(context, LoginActivity::class.java)
            startActivityForResult(intent, SHOW_LOGIN_ACTIVITY)
        }


    }

    /**
     * Dispatch incoming result to the correct fragment.
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == SHOW_LOGIN_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                val email: String? = data?.getStringExtra(Constants.SIGN_IN_DATA_EMAIL)
                val password: String? = data?.getStringExtra(Constants.SIGN_IN_DATA_PASSWORD)
                signInViewModel?.login(email, password)!!.observe(this, Observer<DataWrapper<String>> { t ->
                    /**
                     * Called when the data is changed.
                     * @param t  The new data
                     */
                    if (t!!.throwable != null) {
                        showLogInFailedDialog()
                    } else {
                        Dynamic.uuid = t.data
                        showLogInSuccessDialog()
                    }
                })
            } else if (resultCode == Activity.RESULT_CANCELED) {
                if (data?.action.equals(Constants.ACTION_SHOW_SIGN_UP_DIALOG)) {
                    val intent = Intent(context, SignUpActivity::class.java)
                    intent.putParcelableArrayListExtra(Constants.EXTRA_REGIONS_LIST, Dynamic.regionsModel!!.regions.toCollection(ArrayList()))
                    startActivityForResult(intent, SHOW_SIGN_UP_ACTIVITY)
                } else if (data?.action.equals(Constants.ACTION_SHOW_FORGET_PASSWORD_DIALOG)) {
                    val intent = Intent(context, ForgetPwdActivity::class.java)
                    startActivityForResult(intent, SHOW_FORGET_PASSWORD_ACTIVITY)
                } else if (data?.action.equals(Constants.ACTION_OPERATION_CANCELLED)) {
                    showExitDialog()
                }
            }
        } else if (requestCode == SHOW_SIGN_UP_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                val name: String? = data?.getStringExtra(Constants.NAME)
                val phone: String? = data?.getStringExtra(Constants.PHONE)
                val email: String? = data?.getStringExtra(Constants.EMAIL)
                val joinDate: Long? = data?.getLongExtra(Constants.JOIN_DATE, 0)
                val livingArea: RegionModel? = data?.getParcelableExtra(Constants.LIVING_AREA)
                val password: String? = data?.getStringExtra(Constants.PASSWORD)
                val acceptEula: Boolean? = data?.getBooleanExtra(Constants.ACCEPT_EULA, false)
                val acceptOffer: Boolean? = data?.getBooleanExtra(Constants.ACCEPT_OFFER, false)
                signInViewModel?.signUp(email, name, phone, joinDate, livingArea, password, acceptEula,
                        acceptOffer)!!.observe(this, Observer<DataWrapper<String>> { t ->
                    /**
                     * Called when the data is changed.
                     * @param t  The new data
                     */
                    if (t!!.throwable != null) {
                        showSignUpFailedDialog()
                    } else {
                        Dynamic.uuid = t.data
                        showSignUpSuccessDialog()
                    }
                })
            } else if (resultCode == Activity.RESULT_CANCELED) {
                if (data?.action.equals(Constants.ACTION_SHOW_SIGN_IN_DIALOG)) {
                    val intent = Intent(context, LoginActivity::class.java)
                    startActivityForResult(intent, SHOW_LOGIN_ACTIVITY)
                } else if (data?.action.equals(Constants.ACTION_SHOW_FORGET_PASSWORD_DIALOG)) {
                    val intent = Intent(context, ForgetPwdActivity::class.java)
                    startActivityForResult(intent, SHOW_FORGET_PASSWORD_ACTIVITY)
                } else if (data?.action.equals(Constants.ACTION_OPERATION_CANCELLED)) {
                    showExitDialog()
                }
            }
        } else if (requestCode == SHOW_FORGET_PASSWORD_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                val email: String? = data?.getStringExtra(Constants.EMAIL)
                signInViewModel!!.recoverPassword(email).observe(this, Observer<DataWrapper<String>> { t ->
                    /**
                     * Called when the data is changed.
                     * @param t  The new data
                     */
                    if (t!!.throwable != null) {
                        showRecoverPasswordFailedDialog()
                    } else {
                        val newPassword: String = t.data
                        showRecoverPasswordSuccessDialog()
                    }
                })
            }
            val intent = Intent(context, LoginActivity::class.java)
            startActivityForResult(intent, SHOW_LOGIN_ACTIVITY)
        }
    }

    private fun showLogInSuccessDialog() {
        val dialog = LogInSuccessDialog()
        dialog.show(fragmentManager, LogInSuccessDialog::class.java.simpleName)
    }

    private fun showLogInFailedDialog() {
        val dialog = LogInFailedDialog()
        dialog.show(fragmentManager, LogInFailedDialog::class.java.simpleName)
    }

    private fun showRecoverPasswordSuccessDialog() {
        val dialog = RecoverPasswordSuccessDialog()
        dialog.show(fragmentManager, RecoverPasswordSuccessDialog::class.java.simpleName)
    }

    private fun showRecoverPasswordFailedDialog() {
        val dialog = RecoverPasswordFailedDialog()
        dialog.show(fragmentManager, RecoverPasswordFailedDialog::class.java.simpleName)
    }

    private fun showSignUpSuccessDialog() {
        val dialog = SignUpSuccessDialog()
        dialog.show(fragmentManager, SignUpSuccessDialog::class.java.simpleName)
    }

    private fun showSignUpFailedDialog() {
        val dialog = SignUpFailedDialog()
        dialog.show(fragmentManager, SignUpFailedDialog::class.java.simpleName)
    }

    private fun showExitDialog() {
        val appExitDialog = AppExitDialog()
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