package com.em_projects.tweetings.view.main.menu

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import com.em_projects.tweetings.BaseActivity
import com.em_projects.tweetings.R
import com.em_projects.tweetings.config.Constants
import com.em_projects.tweetings.config.Dynamic
import com.em_projects.tweetings.model.DataWrapper
import com.em_projects.tweetings.model.RegionModel
import com.em_projects.tweetings.model.RegionsModel
import com.em_projects.tweetings.view.main.dialogs.*
import com.em_projects.tweetings.view.main.menu.fragments.*
import com.em_projects.tweetings.view.main.signinup.ForgetPwdActivity
import com.em_projects.tweetings.view.main.signinup.LoginActivity
import com.em_projects.tweetings.view.main.signinup.SignUpActivity
import com.em_projects.tweetings.viewmodel.signinup.SignInViewModel
import kotlinx.android.synthetic.main.activity_drawer.*


// Ref: https://developer.android.com/training/implementing-navigation/nav-drawer#OpenClose
// Ref: https://stackoverflow.com/questions/32774757/add-custom-layout-to-toolbar
// Ref: https://stackoverflow.com/questions/35547074/how-to-change-the-color-of-the-drawer-icon-in-toolbar
// Ref: https://stackoverflow.com/questions/30621561/disable-icon-colorstatelist-in-navigationview/30632980#30632980
// Ref: https://stackoverflow.com/questions/30625280/how-to-create-a-simple-divider-in-the-new-navigationview
// Ref: https://stackoverflow.com/a/41478759/341497 - Vertical divider

class DrawerActivity : BaseActivity(), View.OnClickListener {
    private val TAG = "DrawerActivity"

    private val SHOW_LOGIN_ACTIVITY: Int = 123
    private val SHOW_SIGN_UP_ACTIVITY = SHOW_LOGIN_ACTIVITY + 1
    private val SHOW_FORGET_PASSWORD_ACTIVITY = SHOW_SIGN_UP_ACTIVITY + 1

    private var context: Context? = null

    private var signInViewModel: SignInViewModel? = null

    private var regionList: List<RegionModel>? = null

    // Buttons layouts
    private lateinit var nav_new_user: View
    private lateinit var nav_exist_user: View

    // UI Components
    lateinit var menuButton: ImageView
    var isFragmentLoaded: Boolean = false
    var menuFragment: MenuFragment? = null
    var isAnimating: Boolean = false
    lateinit var screenShadow: View

    // Fragment Constants
    private val OFFER_FRAGMENT: Int = 1
    private val BENEFITS_FRAGMENT: Int = 2
    private val STUDIES_FRAGMENT: Int = 3
    private val WORKS_FRAGMENT: Int = 4
    private val CONTACT_US_FRAGMENT: Int = 5
    private val REGULATIONS_FRAGMENT: Int = 6
    private val ADD_WITH_US_FRAGMENT: Int = 7

    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAddlayout(R.layout.activity_drawer)
        Log.d(TAG, "onCreate")
        context = this

        screenShadow = findViewById(R.id.screenShadow)
        menuButton = findViewById(R.id.menu_icon)
        menuButton.setOnClickListener { v ->
            if (!isFragmentLoaded) {
                showMenu()
            } else {
                if (menuFragment != null) {
                    if (menuFragment!!.isAdded) {
                        hideMenu()
                    }
                }
            }
        }

        // Bind "buttons"
        nav_new_user = findViewById(R.id.nav_new_user)
        nav_exist_user = findViewById(R.id.nav_exist_user)
        findViewById<ImageButton>(R.id.closeDrawerButton).setOnClickListener(this)
        findViewById<Button>(R.id.navLoginButton).setOnClickListener(this)
        findViewById<Button>(R.id.navRegisterButton).setOnClickListener(this)
        findViewById<Button>(R.id.navPersonalButton).setOnClickListener(this)
        findViewById<Button>(R.id.navLogoutButton).setOnClickListener(this)
        findViewById<View>(R.id.benefitsMenuItem).setOnClickListener(this)
        findViewById<View>(R.id.studiesMenuItem).setOnClickListener(this)
        findViewById<View>(R.id.worksMenuItem).setOnClickListener(this)
        findViewById<View>(R.id.contactMenuItem).setOnClickListener(this)
        findViewById<View>(R.id.agreementMenuItem).setOnClickListener(this)
        findViewById<View>(R.id.adMenuItem).setOnClickListener(this)

        signInViewModel = ViewModelProviders.of(this).get(SignInViewModel::class.java)

        // Loading the regions list
        signInViewModel!!.getRegionsList().observe(this, Observer<DataWrapper<RegionsModel>> { dataWrapper ->
            if (dataWrapper?.throwable != null) {
                Toast.makeText(this@DrawerActivity, "Error: " +
                        dataWrapper.throwable.message, Toast.LENGTH_LONG).show()
            } else {
                regionList = dataWrapper!!.data.regions.toList() //(dataWrapper?.data)!!.toCollection(ArrayList<RegionModel>())
            }
        })

        handler = Handler(mainLooper)

        // Binding component in the Header
        loadFragment(OFFER_FRAGMENT)
    }

    private fun hideMenu() {
        if (isFragmentLoaded && !isAnimating) {
            var slideOutAnimation: Animation = AnimationUtils.loadAnimation(context, R.anim.activity_slide_out)
            mainMenuLayout.startAnimation(slideOutAnimation)
            slideOutAnimation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(animation: Animation?) {

                }

                override fun onAnimationEnd(animation: Animation?) {
                    isAnimating = false
                    isFragmentLoaded = false
                    mainMenuLayout.visibility = View.GONE
                    screenShadow.visibility = View.GONE
                }

                override fun onAnimationStart(animation: Animation?) {
                    isAnimating = true
                    screenShadow.visibility = View.GONE
                }

            })
        }
    }


    private fun showMenu() {
        if (!isFragmentLoaded && !isAnimating) {
            mainMenuLayout.visibility = View.VISIBLE
            if (Dynamic.uuid == null) {
                nav_new_user.visibility = View.VISIBLE
                nav_exist_user.visibility = View.GONE
            } else {
                nav_new_user.visibility = View.GONE
                nav_exist_user.visibility = View.VISIBLE
            }
            var slideInAnimation: Animation = AnimationUtils.loadAnimation(context, R.anim.activity_slide_in)
            mainMenuLayout.startAnimation(slideInAnimation)
            slideInAnimation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(animation: Animation?) {

                }

                override fun onAnimationEnd(animation: Animation?) {
                    isAnimating = false
                    isFragmentLoaded = true
                }

                override fun onAnimationStart(animation: Animation?) {
                    isAnimating = true
                    screenShadow.visibility = View.VISIBLE
                }

            })
        }
    }

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.navLoginButton -> {
                val intent = Intent(context, LoginActivity::class.java)
                startActivityForResult(intent, SHOW_LOGIN_ACTIVITY)
            }
            R.id.navRegisterButton -> {
                val intent = Intent(context, SignUpActivity::class.java)
                intent.putParcelableArrayListExtra(Constants.EXTRA_REGIONS_LIST, Dynamic.regionsModel!!.regions.toCollection(ArrayList()))
                startActivityForResult(intent, SHOW_SIGN_UP_ACTIVITY)
            }
            R.id.navPersonalButton -> {
            }
            R.id.navLogoutButton -> {
                showExitDialog()
            }
            R.id.closeDrawerButton -> {
                hideMenu()
            }
            R.id.benefitsMenuItem -> {
                Log.d(TAG, "nav_offers")
                loadFragment(BENEFITS_FRAGMENT)
            }
            R.id.studiesMenuItem -> {
                Log.d(TAG, "nav_studies")
                loadFragment(STUDIES_FRAGMENT)
            }
            R.id.worksMenuItem -> {
                Log.d(TAG, "nav_jobs")
                loadFragment(WORKS_FRAGMENT)
            }
            R.id.contactMenuItem -> {
                Log.d(TAG, "nav_contact_us")
                loadFragment(CONTACT_US_FRAGMENT)
            }
            R.id.agreementMenuItem -> {
                Log.d(TAG, "nav_conditions")
                loadFragment(REGULATIONS_FRAGMENT)
            }
            R.id.adMenuItem -> {
                Log.d(TAG, "nav_ad")
                loadFragment(ADD_WITH_US_FRAGMENT)
            }
        }
        hideMenu()
    }

    private fun loadFragment(fragmentCode: Int) {
        val fragment: Fragment? = getFragmentByCode(fragmentCode)
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
            return HomeFragment()
        } else if (fragmentCode == BENEFITS_FRAGMENT) {
            return BenefitsFragment()
        } else if (fragmentCode == STUDIES_FRAGMENT) {
            return StudiesFragment()
        } else if (fragmentCode == WORKS_FRAGMENT) {
            return WorkFragment()
        } else if (fragmentCode == CONTACT_US_FRAGMENT) {
            return ContactUsFragment()
        } else if (fragmentCode == REGULATIONS_FRAGMENT) {
            return RegulationsFragment()
        } else if (fragmentCode == ADD_WITH_US_FRAGMENT) {
            return AdWithUsFragment()
        }
        // TODO
        return null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == SHOW_LOGIN_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                val email: String? = data?.getStringExtra(Constants.SIGN_IN_DATA_EMAIL)
                val password: String? = data?.getStringExtra(Constants.SIGN_IN_DATA_PASSWORD)
                signInViewModel?.login(email, password)!!.observe(this, object : Observer<DataWrapper<String>> {
                    /**
                     * Called when the data is changed.
                     * @param t  The new data
                     */
                    override fun onChanged(t: DataWrapper<String>?) {
                        if (t!!.throwable != null) {
                            val loginFailedDialog = LogInFailedDialog()
                            val args = Bundle()
                            args.putString(Constants.EXTRA_DATA, t.throwable.message)
                            loginFailedDialog.arguments = args
                            loginFailedDialog.show(supportFragmentManager, loginFailedDialog::class.java.simpleName)

                        } else if (t.data != null) {
                            val loginSuccessDialog = LogInSuccessDialog()
                            val args = Bundle()
                            args.putString(Constants.EXTRA_DATA, t.data)
                            loginSuccessDialog.arguments = args
                            loginSuccessDialog.show(supportFragmentManager, loginSuccessDialog::class.java.simpleName)
                        }
                    }

                })
            } else if (resultCode == Activity.RESULT_CANCELED) {
                if (data?.action.equals(Constants.ACTION_SHOW_SIGN_UP_DIALOG)) {
                    val intent = Intent(context, SignUpActivity::class.java)
                    intent.putParcelableArrayListExtra(Constants.EXTRA_REGIONS_LIST, ArrayList(regionList))
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
                val livingArea: RegionModel? = data?.getParcelableExtra(Constants.LIVING_AREA)
                val password: String? = data?.getStringExtra(Constants.PASSWORD)
                val acceptEula: Boolean? = data?.getBooleanExtra(Constants.ACCEPT_EULA, false)
                val acceptOffer: Boolean? = data?.getBooleanExtra(Constants.ACCEPT_OFFER, false)
                signInViewModel?.signUp(email, name, phone, joinDate, livingArea,
                        password, acceptEula, acceptOffer)!!.observe(this, Observer<DataWrapper<String>> { t ->
                    /**
                     * Called when the data is changed.
                     * @param t  The new data
                     */
                    if (t!!.throwable != null) {
                        val signUpFailedDialog = SignUpFailedDialog()
                        val args = Bundle()
                        args.putString(Constants.EXTRA_DATA, t.throwable.message)
                        signUpFailedDialog.arguments = args
                        signUpFailedDialog.show(supportFragmentManager, signUpFailedDialog::class.java.simpleName)

                    } else if (t.data != null) {
                        val signUpSuccessDialog = SignUpSuccessDialog()
                        val args = Bundle()
                        args.putString(Constants.EXTRA_DATA, t.data)
                        signUpSuccessDialog.arguments = args
                        signUpSuccessDialog.show(supportFragmentManager, signUpSuccessDialog::class.java.simpleName)
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
        val fragmentManager: FragmentManager = supportFragmentManager
        val appExitDialog = AppExitDialog()
        appExitDialog.show(fragmentManager, "AppExitDialog")
    }

    override fun onBackPressed() {
        if (isFragmentLoaded) {
            hideMenu()
        } else {
//            super.onBackPressed()
            showExitDialog()
        }
    }
}
