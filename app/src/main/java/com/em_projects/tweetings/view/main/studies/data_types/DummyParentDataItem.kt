package com.em_projects.tweetings.view.main.studies.data_types

import java.io.Serializable

class DummyParentDataItem : Serializable {

    private var name: String? = null
    private var children: ArrayList<DummyChildDataItem>? = null

    fun getParentName(): String? {
        return name
    }

    fun setParentName(name: String) {
        this.name = name
    }

    fun getChildDataItems(): ArrayList<DummyChildDataItem>? {
        return children
    }

    fun setChildDataItems(childArray: ArrayList<DummyChildDataItem>) {
        this.children = childArray
    }
}