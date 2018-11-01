package com.em_projects.tweetings.view.main.menu

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
import android.widget.TextView
import com.em_projects.tweetings.R
import com.em_projects.tweetings.view.main.menu.fragments.OfferFragment
import kotlinx.android.synthetic.main.activity_drawer.*
import kotlinx.android.synthetic.main.app_bar_drawer.*

// Ref: https://developer.android.com/training/implementing-navigation/nav-drawer#OpenClose
// Ref: https://stackoverflow.com/questions/32774757/add-custom-layout-to-toolbar
// Ref: https://stackoverflow.com/questions/35547074/how-to-change-the-color-of-the-drawer-icon-in-toolbar

class DrawerActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private val TAG = "DrawerActivity"

    // Fragment Constants
    private val OFFER_FRAGMENT: Int = 1

    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawer)
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.rounded_rectangle_2);
//        toolbar.getNavigationIcon()!!.setColorFilter(Color.BLACK, PorterDuff.Mode.MULTIPLY)

        handler = Handler(mainLooper)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        toggle.drawerArrowDrawable.color = resources.getColor(android.R.color.black)

        drawer_layout.addDrawerListener(
                object : DrawerLayout.DrawerListener {
                    override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                        Log.d("TAG", "onDrawerSlide")
                    }

                    override fun onDrawerOpened(drawerView: View) {
                        Log.d("TAG", "onDrawerOpened")
                    }

                    override fun onDrawerClosed(drawerView: View) {
                        Log.d("TAG", "onDrawerClosed")
                    }

                    override fun onDrawerStateChanged(newState: Int) {
                        Log.d("TAG", "onDrawerStateChanged")
                    }
                }
        )

        // Binding component in the Header
        var navView = nav_view.getHeaderView(0)
        var headerTextView = navView.findViewById<TextView>(R.id.headerTextView)
        var text1 = headerTextView.text
        Log.d("TAG", text1.toString())

        nav_view.setNavigationItemSelectedListener(this)

        var navMenu = nav_view.menu
        for (i in 0..navMenu.size() - 1) {
            Log.d("TAG", navMenu.getItem(i).title.toString())
            if (navMenu.getItem(i).title.toString().equals("Second")) {
                navMenu.getItem(i).setEnabled(false)
            }
        }

        loadFragment(OFFER_FRAGMENT)
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

            if (pendingRunnable != null) {
                handler.post(pendingRunnable);
            }
        }
    }

    private fun getFragmentByCode(fragmentCode: Int): Fragment? {
        if (fragmentCode == OFFER_FRAGMENT) {
            return OfferFragment()
        }
        // TODO
        return null
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
