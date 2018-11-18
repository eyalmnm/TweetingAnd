package com.em_projects.tweetings.viewmodel.signinup

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.em_projects.tweetings.model.DataWrapper

class SignInViewModel(application: Application) : AndroidViewModel(application) {


    fun login(email: String?, password: String?): LiveData<DataWrapper<String>> {
        TODO("not implemented")
    }

    fun signUp(name: String?, phone: String?, email: String?, joinDate: Long?, livingArea: String?,
               password: String?, acceptEula: Boolean?, acceptOffer: Boolean?): LiveData<DataWrapper<String>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun recoverPassword(email: String?): LiveData<DataWrapper<String>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        TODO("not implemented") // Show *Forgot Password2* screen as results for successfully sending email
    }

    fun getRegionsList(): LiveData<DataWrapper<String>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}