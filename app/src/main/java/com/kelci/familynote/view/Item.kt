package com.kelci.familynote.view

interface Item {

    fun isSection(): Boolean
    fun getTitle(): String
    fun getSubtitle(): String
}