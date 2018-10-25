package com.em_projects.tweetings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.em_projects.tweetings.view.main.MainScreenActivity

https://stackoverflow.com/questions/5745814/android-change-horizontal-progress-bar-color

class MainActivity : AppCompatActivity() {
    private val TAG: String = "MainActivity";

    private var context: Context? = null
    private val displayTime: Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate")
        context = this

        Handler(Looper.getMainLooper()).postDelayed(Runnable { moveToNextScreen() }, displayTime)
    }

    fun moveToNextScreen() {
        var intent: Intent = Intent(context, MainScreenActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        overridePendingTransition(R.anim.activity_slide_in, R.anim.activity_slide_out)
        finish()
    }
}
