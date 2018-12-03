package com.em_projects.tweetings.viewmodel.signinup

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.em_projects.tweetings.config.Constants
import com.em_projects.tweetings.config.Dynamic
import com.em_projects.tweetings.model.DataWrapper
import com.em_projects.tweetings.model.RegionModel
import com.em_projects.tweetings.remote.ApiConnector
import com.em_projects.tweetings.remote.ApiController
import com.em_projects.tweetings.utils.RetrofitUtils
import com.em_projects.tweetings.utils.TimeUtils
import retrofit2.Call
import retrofit2.Callback;
import retrofit2.Response

// Ref: https://stackoverflow.com/questions/49573168/how-to-use-enque-method-in-retrofit-in-kotlin-android

class SignInViewModel(application: Application) : AndroidViewModel(application) {
    private val TAG: String = "SignInViewModel"

    private var regionsList: List<RegionModel>? = null
    private var appToken: String = Constants.APP_TOKEN

    fun login(email: String?, password: String?): LiveData<DataWrapper<String>> {
        val liveData: MutableLiveData<DataWrapper<String>> = MutableLiveData()
        val dataWrapper: DataWrapper<String> = DataWrapper()

        val apiConnector: ApiConnector = ApiController.createService(ApiConnector::class.java)
        apiConnector.loginRequest(appToken, email, password).enqueue(object : Callback<String> {

            override fun onFailure(call: Call<String>, t: Throwable) {
                dataWrapper.throwable = t
                liveData.value = dataWrapper
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    Dynamic.uuid = response.body()
                    dataWrapper.data = Dynamic.uuid
                } else {
                    dataWrapper.throwable = Throwable(RetrofitUtils.handleErrorResponse(response))
                }
                liveData.value = dataWrapper
            }
        })

        return liveData
    }

    fun logout(): LiveData<DataWrapper<String>> {
        val liveData: MutableLiveData<DataWrapper<String>> = MutableLiveData()
        val dataWrapper: DataWrapper<String> = DataWrapper()

        val apiConnector: ApiConnector = ApiController.createService(ApiConnector::class.java)
        apiConnector.logoutRequest(appToken, Dynamic.uuid).enqueue(object : Callback<String> {

            override fun onFailure(call: Call<String>, t: Throwable) {
                dataWrapper.throwable = t
                liveData.value = dataWrapper
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    dataWrapper.data = response.body()
                } else {
                    dataWrapper.throwable = Throwable(RetrofitUtils.handleErrorResponse(response))
                }
                liveData.value = dataWrapper
            }

        })
        return liveData
    }

    fun signUp(email: String?, name: String?, phone: String?, joinDate: Long?, livingArea: String?,
               password: String?, acceptEula: Boolean?, acceptOffer: Boolean?): LiveData<DataWrapper<String>> {
        val liveData: MutableLiveData<DataWrapper<String>> = MutableLiveData()
        val dataWrapper: DataWrapper<String> = DataWrapper()

        if (Dynamic.uuid == null) {
            val joinDateStr: String? = TimeUtils.getRegDateStr(joinDate!!)
            val eulaInt = if (acceptEula!!) {
                1
            } else {
                0
            }
            val offerInt = if (acceptOffer!!) {
                1
            } else {
                0
            }
            val apiConnector: ApiConnector = ApiController.createService(ApiConnector::class.java)
            apiConnector.registrationRequest(appToken, email, name, phone, joinDateStr, livingArea,
                    password, password, eulaInt, offerInt).enqueue(object : Callback<String> {

                override fun onFailure(call: Call<String>, t: Throwable) {
                    dataWrapper.throwable = t
                    liveData.value = dataWrapper
                }

                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        dataWrapper.data = "Registration successfull"
                    } else {
                        dataWrapper.throwable = Throwable(RetrofitUtils.handleErrorResponse(response))
                    }
                    liveData.value = dataWrapper
                }

            })
        } else {
            dataWrapper.data = "success"
            liveData.value = dataWrapper
        }
        return liveData
    }

    fun recoverPassword(email: String?): LiveData<DataWrapper<String>> {
        val liveData: MutableLiveData<DataWrapper<String>> = MutableLiveData()
        val dataWrapper: DataWrapper<String> = DataWrapper()

        val apiConnector: ApiConnector = ApiController.createService(ApiConnector::class.java)
        apiConnector.passwordResetRequest(appToken, email).enqueue(object : Callback<String> {

            override fun onFailure(call: Call<String>, t: Throwable) {
                dataWrapper.throwable = t
                liveData.value = dataWrapper
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    dataWrapper.data = response.body()
                } else {
                    dataWrapper.throwable = Throwable(RetrofitUtils.handleErrorResponse(response))
                }
                liveData.value = dataWrapper
            }

        })
        return liveData
    }

    fun editUser(email: String?, name: String?, phone: String?, joinDate: Long?, livingArea: RegionModel?,
                 currentPassword: String?, newPassword: String?, isMale: Boolean): LiveData<DataWrapper<String>> {
        val liveData: MutableLiveData<DataWrapper<String>> = MutableLiveData()
        val dataWrapper: DataWrapper<String> = DataWrapper()

        val apiConnector: ApiConnector = ApiController.createService(ApiConnector::class.java)
        val joinDateStr: String? = TimeUtils.getRegDateStr(joinDate!!)
        val sexInt = if (isMale) {
            1
        } else {
            2
        }
        apiConnector.editUserDataRequest(appToken, email, name, phone, joinDateStr, livingArea!!.title,
                currentPassword, newPassword, newPassword, sexInt, Dynamic.uuid).enqueue(object : Callback<String> {

            override fun onFailure(call: Call<String>, t: Throwable) {
                dataWrapper.throwable = t
                liveData.value = dataWrapper
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
//                    Dynamic.uuid = response.body()  // TODO Check on real time
                    dataWrapper.data = response.body()
                } else {
                    dataWrapper.throwable = Throwable(RetrofitUtils.handleErrorResponse(response))
                }
                liveData.value = dataWrapper
            }

        })
        return liveData
    }

    fun getRegionsList(): LiveData<DataWrapper<List<RegionModel>>> {
        val liveData: MutableLiveData<DataWrapper<List<RegionModel>>> = MutableLiveData()
        val dataWrapper: DataWrapper<List<RegionModel>> = DataWrapper()

        if (regionsList == null) {
            val apiConnector: ApiConnector = ApiController.createService(ApiConnector::class.java)
            apiConnector.regionsRequest(appToken).enqueue(object : Callback<List<RegionModel>> {

                override fun onFailure(call: Call<List<RegionModel>>, t: Throwable) {
                    Log.d(TAG, "getRegionsList -> onFailure: ${t.message}")
                    dataWrapper.throwable = t
                    liveData.value = dataWrapper
                }

                override fun onResponse(call: Call<List<RegionModel>>, response: Response<List<RegionModel>>) {
                    if (response.isSuccessful) {
                        regionsList = response.body()
                        dataWrapper.data = regionsList
                    } else {
                        dataWrapper.throwable = Throwable(RetrofitUtils.handleErrorResponse(response))
                    }
                    liveData.value = dataWrapper
                }
            })

        } else {
            dataWrapper.data = this.regionsList
            liveData.value = dataWrapper
        }
        return liveData
    }
}