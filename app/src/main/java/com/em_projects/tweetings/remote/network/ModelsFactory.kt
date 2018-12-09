package com.em_projects.tweetings.remote.network

import com.em_projects.tweetings.model.RegionModel
import com.em_projects.tweetings.utils.JSONUtils
import org.json.JSONArray
import org.json.JSONObject

class ModelsFactory {
    companion object {

        fun createRegionModel(jsonObj: JSONObject): RegionModel {
            val id: Int = JSONUtils.getIntValue(jsonObj, "id")
            val title: String = JSONUtils.getStringValue(jsonObj, "itemTitle");
            return RegionModel(id, title)
        }

        fun createRegionsModel(jsonArr: JSONArray): List<RegionModel> {
            var regions: MutableList<RegionModel> = ArrayList<RegionModel>()
            for (i in 0 until jsonArr.length()) {
                regions.add(createRegionModel(jsonArr.getJSONObject(i)))
            }
            return regions
        }

        fun createUUID(jsonObj: JSONObject): String {
            return JSONUtils.getStringValue(jsonObj, "uuid")
        }
    }
}