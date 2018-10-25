package com.em_projects.tweetings.view.main.studies.data_types

import java.io.Serializable

class DummyChildDataItem : Serializable {

    private var childName: String? = null

    fun getChildName(): String? {
        return childName
    }

    fun setChildName(name: String) {
        this.childName = name
    }
}