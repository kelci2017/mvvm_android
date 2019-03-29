package com.kelci.familynote.view.settings

interface Item {

    fun isSection(): Boolean
    fun getTitle(): String
    fun getSubtitle(): String
}