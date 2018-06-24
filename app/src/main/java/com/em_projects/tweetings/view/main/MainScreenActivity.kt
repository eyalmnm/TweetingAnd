package com.em_projects.tweetings.view.main

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.em_projects.tweetings.R
import com.em_projects.tweetings.config.Constants
import com.em_projects.tweetings.config.Dynamic
import com.em_projects.tweetings.utils.StringUtils
import com.em_projects.tweetings.view.main.signinup.LoginActivity
import com.em_projects.tweetings.view.main.signinup.SignUpActivity
import com.em_projects.tweetings.viewmodel.signinup.SignInViewModle
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class MainScreenActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks {
    private val TAG: String = "MainScreenActivity";

    private val RC_INTERNET_PERM = 123
    private val RC_LOCATION_CONTACTS_PERM = 124;

    private val RC_SHOW_LOGIN_ACTIVITY = 125;
    private val RC_SHOW_SIGN_UP_ACTIVITY = 126;

    private var context: Context? = null
    private val permissions: Array<String> = arrayOf(Manifest.permission.INTERNET)

    private var signInViewModel: SignInViewModle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)
        Log.d(TAG, "onCreate")
        signInViewModel = SignInViewModle(this)
        context = this;

        if (EasyPermissions.hasPermissions(context as MainScreenActivity, permissions.toString())) {
            continueLoading()
        } else {
            EasyPermissions.requestPermissions(context as MainScreenActivity,
                    getString(R.string.internet_connection_rationale),
                    RC_INTERNET_PERM,
                    permissions.toString())
        }
    }

    private fun continueLoading() {
        if (StringUtils.isNullOrEmpty(Dynamic.uuid)) {
            var intent = Intent(context, LoginActivity::class.java)
            startActivityForResult(intent, RC_SHOW_LOGIN_ACTIVITY)
        }
    }

    /**
     * Dispatch incoming result to the correct fragment.
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_SHOW_LOGIN_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                var email: String? = data?.getStringExtra(Constants.SIGN_IN_DATA_EMAIL)
                var password: String? = data?.getStringExtra(Constants.SIGN_IN_DATA_PASSWORD)
                signInViewModel?.login(email, password)
            } else if (resultCode == Activity.RESULT_CANCELED) {
                if (data?.action.equals(Constants.ACTION_SHOW_SIGN_UP_DIALOG)) {
                    var intent = Intent(context, SignUpActivity::class.java)
                    startActivityForResult(intent, RC_SHOW_SIGN_UP_ACTIVITY)
                } else if (data?.action.equals(Constants.ACTION_OPERATION_CANCELLED)) {
                    Log.e(TAG, "Handle operation cancelled")
                }
            }
        } else if (requestCode == RC_SHOW_SIGN_UP_ACTIVITY) {
            TODO("not implemented")
        }
    }

    // **********************************   PERMISSIONS SECTION   **********************************
    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        Log.d(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size)

        // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
        // This will display a dialog directing them to enable the permission in app settings.
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        Log.d(TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size)
        continueLoading()
    }

    override fun onRationaleDenied(requestCode: Int) {
        Log.d(TAG, "onRationaleDenied:$requestCode")
    }

    override fun onRationaleAccepted(requestCode: Int) {
        Log.d(TAG, "onRationaleAccepted:$requestCode")
    }
    // **********************************   PERMISSIONS SECTION   **********************************
}