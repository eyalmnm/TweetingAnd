package com.em_projects.tweetings.viewmodel.signinup

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.em_projects.tweetings.config.Constants
import com.em_projects.tweetings.config.Dynamic
import com.em_projects.tweetings.model.DataWrapper
import com.em_projects.tweetings.model.RegionModel
import com.em_projects.tweetings.model.RegionsModel
import com.em_projects.tweetings.remote.network.CommListener
import com.em_projects.tweetings.remote.network.Communicator
import com.em_projects.tweetings.remote.network.ModelsFactory
import com.em_projects.tweetings.utils.JSONUtils
import com.em_projects.tweetings.utils.TimeUtils
import org.json.JSONArray
import org.json.JSONObject

// Ref: https://stackoverflow.com/questions/49573168/how-to-use-enque-method-in-retrofit-in-kotlin-android

class SignInViewModel(application: Application) : AndroidViewModel(application) {
    private val TAG: String = "SignInViewModel"

    private var regionsModel: RegionsModel? = null
    private var appToken: String = Constants.APP_TOKEN

    // Convert List To Array
    inline fun <reified T> toArray(list: List<*>): Array<T> {
        return (list as List<T>).toTypedArray()
    }

    fun login(email: String?, password: String?): LiveData<DataWrapper<String>> {
        val liveData: MutableLiveData<DataWrapper<String>> = MutableLiveData()
        val dataWrapper: DataWrapper<String> = DataWrapper()

        // TODO
        return liveData
    }

    fun logout(): LiveData<DataWrapper<String>> {
        val liveData: MutableLiveData<DataWrapper<String>> = MutableLiveData()
        val dataWrapper: DataWrapper<String> = DataWrapper()

        // TODO
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

            // TODO
        } else {
            dataWrapper.data = "success"
            liveData.value = dataWrapper
        }
        return liveData
    }

    fun recoverPassword(email: String?): LiveData<DataWrapper<String>> {
        val liveData: MutableLiveData<DataWrapper<String>> = MutableLiveData()
        val dataWrapper: DataWrapper<String> = DataWrapper()

        // TODO
        return liveData
    }

    fun editUser(email: String?, name: String?, phone: String?, joinDate: Long?, livingArea: RegionModel?,
                 currentPassword: String?, newPassword: String?, isMale: Boolean): LiveData<DataWrapper<String>> {
        val liveData: MutableLiveData<DataWrapper<String>> = MutableLiveData()
        val dataWrapper: DataWrapper<String> = DataWrapper()

        val joinDateStr: String? = TimeUtils.getRegDateStr(joinDate!!)
        val sexInt = if (isMale) {
            1
        } else {
            2
        }

        // TODO
        return liveData
    }

    fun getRegionsList(): LiveData<DataWrapper<RegionsModel>> {
        val liveData: MutableLiveData<DataWrapper<RegionsModel>> = MutableLiveData()
        val dataWrapper: DataWrapper<RegionsModel> = DataWrapper()

        if (regionsModel == null) {
            Communicator.getInstance().getRegions(object : CommListener {
                override fun exceptionThrown(throwable: Throwable?) {
                    dataWrapper.throwable = throwable
                    liveData.value = dataWrapper
                }

                override fun newDataArrived(response: String?) {
                    var jsonObject: JSONObject = JSONObject(response)
                    var dataObject: JSONObject = JSONUtils.getJSONObjectValue(jsonObject, "data");
                    var jsonArr: JSONArray = JSONUtils.getJsonArray(dataObject, "regions")
                    var list: List<RegionModel> = ModelsFactory.createRegionsModel(jsonArr)
                    regionsModel = RegionsModel(toArray<RegionModel>(list))

                    dataWrapper.data = regionsModel
                    liveData.value = dataWrapper
                }
            })
        } else {
            dataWrapper.data = this.regionsModel
            liveData.value = dataWrapper
        }
        return liveData
    }
}