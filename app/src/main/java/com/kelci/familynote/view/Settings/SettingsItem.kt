package com.kelci.familynote.view.Settings

import com.kelci.familynote.view.Settings.Item

class SettingsItem(title : String, subtitle : String) : Item {

    private var title : String = ""
    private var subtitle : String = ""

    init {
        this.title = title
        this.subtitle = subtitle
    }

    override fun getTitle(): String {
        return title
    }
    override fun isSection() : Boolean {
        return false
    }

    override fun getSubtitle() : String {
        return subtitle
    }
}