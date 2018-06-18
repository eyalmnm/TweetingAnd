package com.em_projects.tweetings.main

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.em_projects.tweetings.R
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.AppSettingsDialog



class MainScreenActivity: AppCompatActivity(), EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks {
    private val TAG: String = "MainScreenActivity";

    private val RC_INTERNET_PERM = 123
    private val RC_LOCATION_CONTACTS_PERM = 124;

    private var context: Context? = null
    private val permissions: Array<String> = arrayOf(Manifest.permission.INTERNET)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)
        Log.d(TAG, "onCreate")

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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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