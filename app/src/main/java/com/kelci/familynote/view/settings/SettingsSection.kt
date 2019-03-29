package com.kelci.familynote.view.settings

class SettingsSection(title : String) : Item {

    private var title : String = ""

    init {
        this.title = title
    }

    override fun getTitle(): String {
        return title
    }
    override fun isSection() : Boolean {
        return true
    }

    override fun getSubtitle(): String {
        return ""
    }
}