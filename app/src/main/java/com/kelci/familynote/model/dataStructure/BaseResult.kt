package com.kelci.familynote.model.dataStructure

open class BaseResult constructor(
        var resultCode : Int =0,
        var resultDesc :Any? = null
) {
    fun isSuccess(): Boolean {
        when (resultCode == 0) {
            true -> return true
            false -> return false
        }
    }
}