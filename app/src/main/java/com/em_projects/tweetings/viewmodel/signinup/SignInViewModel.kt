package com.em_projects.tweetings.viewmodel.signinup

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.em_projects.tweetings.model.drawerModels.DataWrapper

class SignInViewModel(application: Application) : AndroidViewModel(application) {


    fun login(email: String?, password: String?): LiveData<DataWrapper<String>> {
        TODO("not implemented")
    }
}