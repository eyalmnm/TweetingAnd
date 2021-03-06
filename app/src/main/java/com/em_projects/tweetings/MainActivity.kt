package com.em_projects.tweetings

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ProgressBar
import android.widget.Toast
import com.em_projects.tweetings.model.DataWrapper
import com.em_projects.tweetings.model.RegionsModel
import com.em_projects.tweetings.view.main.menu.DrawerActivity
import com.em_projects.tweetings.viewmodel.signinup.SignInViewModel


// Ref: https://stackoverflow.com/questions/5745814/android-change-horizontal-progress-bar-color

class MainActivity : AppCompatActivity() {
    private val TAG: String = "MainActivity";

    // Timer to update the elapsedTime display
    private val mFrequency: Long = 50    // milliseconds
    private val TICK_WHAT = 2
    private var percentage = 0
    @SuppressLint("HandlerLeak")
    private val mHandler = object : Handler() {
        override fun handleMessage(m: Message) {
            updateElapsedTime()
            if (100 >= percentage) {
                sendMessageDelayed(Message.obtain(this, TICK_WHAT), mFrequency)
            } else {
                if (canContinueToNext)
                    moveToNextScreen()
            }
        }
    }
    private var canContinueToNext: Boolean = true;

    private var context: Context? = null
    private val displayTime: Long = 3000

    private var signInViewModel: SignInViewModel? = null

    // UI Components
    private var progressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate")
        context = this

        signInViewModel = ViewModelProviders.of(this).get(SignInViewModel::class.java);
        signInViewModel!!.getRegionsList().observe(this, Observer<DataWrapper<RegionsModel>> { t ->
            /**
             * Called when the data is changed.
             * @param t  The new data
             */
            if (t!!.throwable != null) {
                Handler(Looper.getMainLooper()).post(object : Runnable {

                    override fun run() {
                        Toast.makeText(context, R.string.connectivity_problemse, Toast.LENGTH_LONG).show()
                    }
                })
                canContinueToNext = false;
            }
        })

        progressBar = findViewById(R.id.ProgressBar)

        mHandler.sendMessageDelayed(Message.obtain(mHandler, TICK_WHAT), mFrequency);
    }

    private fun updateElapsedTime() {
        percentage += 2
        progressBar!!.progress = (percentage)
    }

    fun moveToNextScreen() {
//        var intent: Intent = Intent(context, MainScreenActivity::class.java)
        var intent: Intent = Intent(context, DrawerActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        overridePendingTransition(R.anim.activity_slide_in, R.anim.activity_slide_out)
        finish()
    }
}
