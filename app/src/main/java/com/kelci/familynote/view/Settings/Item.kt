package com.kelci.familynote.view.Settings

interface Item {

    fun isSection(): Boolean
    fun getTitle(): String
    fun getSubtitle(): String
}