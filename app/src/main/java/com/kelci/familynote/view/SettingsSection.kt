package com.kelci.familynote.view

class SettingsSection(title : String) : Item {

    private var title : String = ""

    init {
        this.title = title
    }

    override fun getTitle(): String {
        return title
    }
    override fun isSection() : Boolean {
        return false
    }

    override fun getSubtitle(): String {
        return ""
    }
}