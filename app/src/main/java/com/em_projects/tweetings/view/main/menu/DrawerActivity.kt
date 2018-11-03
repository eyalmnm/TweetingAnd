package com.em_projects.tweetings.view.main.menu

import android.app.Activity
import android.app.FragmentManager
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.em_projects.tweetings.R
import com.em_projects.tweetings.config.Constants
import com.em_projects.tweetings.config.Dynamic
import com.em_projects.tweetings.utils.StringUtils
import com.em_projects.tweetings.view.main.dialogs.AppExitDialog
import com.em_projects.tweetings.view.main.menu.fragments.OfferFragment
import com.em_projects.tweetings.view.main.signinup.ForgetPwdActivity
import com.em_projects.tweetings.view.main.signinup.LoginActivity
import com.em_projects.tweetings.view.main.signinup.SignUpActivity
import com.em_projects.tweetings.viewmodel.signinup.SignInViewModel
import kotlinx.android.synthetic.main.activity_drawer.*
import kotlinx.android.synthetic.main.app_bar_drawer.*

// Ref: https://developer.android.com/training/implementing-navigation/nav-drawer#OpenClose
// Ref: https://stackoverflow.com/questions/32774757/add-custom-layout-to-toolbar
// Ref: https://stackoverflow.com/questions/35547074/how-to-change-the-color-of-the-drawer-icon-in-toolbar

class DrawerActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private val TAG = "DrawerActivity"

    private val SHOW_LOGIN_ACTIVITY: Int = 123
    private val SHOW_SIGN_UP_ACTIVITY = SHOW_LOGIN_ACTIVITY + 1
    private val SHOW_FORGET_PASSWORD_ACTIVITY = SHOW_SIGN_UP_ACTIVITY + 1

    private var context: Context? = null

    private var signInViewModel: SignInViewModel? = null

    // Buttons layouts
    private lateinit var nav_new_user: View
    private lateinit var nav_exist_user: View

    // Fragment Constants
    private val OFFER_FRAGMENT: Int = 1

    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawer)
        Log.d(TAG, "onCreate")
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.rounded_rectangle_2);
//        toolbar.getNavigationIcon()!!.setColorFilter(Color.BLACK, PorterDuff.Mode.MULTIPLY)

        signInViewModel = ViewModelProviders.of(this).get(SignInViewModel::class.java);
        context = this;

        handler = Handler(mainLooper)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        toggle.drawerArrowDrawable.color = resources.getColor(android.R.color.black)

        drawer_layout.addDrawerListener(
                object : DrawerLayout.DrawerListener {

                    override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                    }

                    override fun onDrawerOpened(drawerView: View) {
                        Log.d(TAG, "onDrawerOpened")
                    }

                    override fun onDrawerClosed(drawerView: View) {
                        Log.d(TAG, "onDrawerClosed")
                    }

                    override fun onDrawerStateChanged(newState: Int) {
                        Log.d(TAG, "onDrawerStateChanged")
                        if (newState == DrawerLayout.STATE_DRAGGING) {
                            Log.d(TAG, "onDrawerStateChanged -> DRAGGING")
                        } else if (newState == DrawerLayout.STATE_IDLE) {
                            Log.d(TAG, "onDrawerStateChanged -> IDLE")
                            if (StringUtils.isNullOrEmpty(Dynamic.uuid)) {
                                nav_new_user.visibility = View.VISIBLE
                                nav_exist_user.visibility = View.GONE
                            } else {
                                nav_new_user.visibility = View.GONE
                                nav_exist_user.visibility = View.VISIBLE
                            }
                        } else if (newState == DrawerLayout.STATE_SETTLING) {
                            Log.d(TAG, "onDrawerStateChanged -> SETTLING")
                        }
                    }
                }
        )

        // Binding component in the Header
        val navView = nav_view.getHeaderView(0)
        val headerTextView = navView.findViewById<TextView>(R.id.headerTextView)
        val text1 = headerTextView.text
        Log.d("TAG", text1.toString())
        nav_new_user = navView.findViewById(R.id.nav_new_user)
        nav_exist_user = navView.findViewById(R.id.nav_exist_user)
        val navLoginButton = navView.findViewById<Button>(R.id.navLoginButton)
        val navRegisterButton = navView.findViewById<Button>(R.id.navRegisterButton)
        val navPersonalButton = navView.findViewById<Button>(R.id.navPersonalButton)
        val navLogoutButton = navView.findViewById<Button>(R.id.navLogoutButton)
        val closeDrawerButton = navView.findViewById<ImageButton>(R.id.closeDrawerButton)

        // Set listener to Header's button
        navLoginButton.setOnClickListener(this)
        navRegisterButton.setOnClickListener(this)
        navPersonalButton.setOnClickListener(this)
        navLogoutButton.setOnClickListener(this)
        closeDrawerButton.setOnClickListener(this)

        nav_view.setNavigationItemSelectedListener(this)

        val navMenu = nav_view.menu
        for (i in 0 until navMenu.size()) {
            Log.d("TAG", navMenu.getItem(i).title.toString())
            if (navMenu.getItem(i).title.toString().equals("Second")) {
                navMenu.getItem(i).setEnabled(false)
            }
        }

        loadFragment(OFFER_FRAGMENT)
    }

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.navLoginButton -> {
                val intent = Intent(context, LoginActivity::class.java)
                startActivityForResult(intent, SHOW_LOGIN_ACTIVITY)
            }
            R.id.navRegisterButton -> {
                val intent = Intent(context, SignUpActivity::class.java)
                startActivityForResult(intent, SHOW_SIGN_UP_ACTIVITY)
            }
            R.id.navPersonalButton -> {
                // TODO
            }
            R.id.navLogoutButton -> {
                showExitDialog()
            }
            R.id.closeDrawerButton -> {
                drawer_layout.closeDrawers()
            }
        }
        drawer_layout.closeDrawers();
    }

    private fun loadFragment(fragmentCode: Int) {
        val fragment: Fragment? = getFragmentByCode(fragmentCode);
        if (fragment != null) {
            val pendingRunnable = Runnable {
                // update the main content by replacing fragments
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out)
                fragmentTransaction.replace(R.id.contentContainer, fragment, fragment.javaClass.simpleName)
                fragmentTransaction.commitAllowingStateLoss()
            }

            handler.post(pendingRunnable)
        }
    }

    private fun getFragmentByCode(fragmentCode: Int): Fragment? {
        if (fragmentCode == OFFER_FRAGMENT) {
            return OfferFragment()
        }
        // TODO
        return null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == SHOW_LOGIN_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                val email: String? = data?.getStringExtra(Constants.SIGN_IN_DATA_EMAIL)
                val password: String? = data?.getStringExtra(Constants.SIGN_IN_DATA_PASSWORD)
                signInViewModel?.login(email, password)
            } else if (resultCode == Activity.RESULT_CANCELED) {
                if (data?.action.equals(Constants.ACTION_SHOW_SIGN_UP_DIALOG)) {
                    val intent = Intent(context, SignUpActivity::class.java)
                    startActivityForResult(intent, SHOW_SIGN_UP_ACTIVITY)
                } else if (data?.action.equals(Constants.ACTION_SHOW_FORGET_PASSWORD_DIALOG)) {
                    val intent = Intent(context, ForgetPwdActivity::class.java)
                    startActivityForResult(intent, SHOW_FORGET_PASSWORD_ACTIVITY)
                } else if (data?.action.equals(Constants.ACTION_OPERATION_CANCELLED)) {
                    // showExitDialog()
                }
            }
        } else if (requestCode == SHOW_SIGN_UP_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                val name: String? = data?.getStringExtra(Constants.NAME)
                val phone: String? = data?.getStringExtra(Constants.PHONE)
                val email: String? = data?.getStringExtra(Constants.EMAIL)
                val joinDate: Long? = data?.getLongExtra(Constants.JOIN_DATE, 0)
                val livingArea: String? = data?.getStringExtra(Constants.LIVING_AREA)
                val password: String? = data?.getStringExtra(Constants.PASSWORD)
                val acceptEula: Boolean? = data?.getBooleanExtra(Constants.ACCEPT_EULA, false)
                val acceptOffer: Boolean? = data?.getBooleanExtra(Constants.ACCEPT_OFFER, false)
                signInViewModel?.signUp(name, phone, email, joinDate, livingArea, password, acceptEula, acceptOffer)
            } else if (resultCode == Activity.RESULT_CANCELED) {
                if (data?.action.equals(Constants.ACTION_SHOW_SIGN_IN_DIALOG)) {
                    val intent = Intent(context, LoginActivity::class.java)
                    startActivityForResult(intent, SHOW_LOGIN_ACTIVITY)
                } else if (data?.action.equals(Constants.ACTION_SHOW_FORGET_PASSWORD_DIALOG)) {
                    val intent = Intent(context, ForgetPwdActivity::class.java)
                    startActivityForResult(intent, SHOW_FORGET_PASSWORD_ACTIVITY)
                } else if (data?.action.equals(Constants.ACTION_OPERATION_CANCELLED)) {
                    // showExitDialog()
                }
            }
        } else if (requestCode == SHOW_FORGET_PASSWORD_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                val email: String? = data?.getStringExtra(Constants.EMAIL)
                signInViewModel!!.recoverPassword(email)
            }
            val intent = Intent(context, LoginActivity::class.java)
            startActivityForResult(intent, SHOW_LOGIN_ACTIVITY)
        }
    }

    private fun showExitDialog() {
        val fragmentManager: FragmentManager = getFragmentManager()
        val appExitDialog = AppExitDialog()
        appExitDialog.show(fragmentManager, "AppExitDialog")
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.drawer, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_offers -> {
                Log.d("TAG", "nav_offers")
            }
            R.id.nav_studies -> {
                Log.d("TAG", "nav_studies")
            }
            R.id.nav_jobs -> {
                Log.d("TAG", "nav_jobs")
            }
            R.id.nav_contact_us -> {
                Log.d("TAG", "nav_contact_us")
            }
            R.id.nav_conditions -> {
                Log.d("TAG", "nav_conditions")
            }
            R.id.nav_ad -> {
                Log.d("TAG", "nav_ad")
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
