package com.kelci.familynote.model.dataStructure

open class BaseResult(resultCode: Int, resultDesc: Any) {

    private var resultCode: Int = 0
    private var resultDesc: Any

    init {
        this.resultCode = resultCode
        this.resultDesc = resultDesc
    }

    fun getResultCode(): Int {
        return this.resultCode
    }

    fun getResultDesc(): Any {
        return this.resultDesc
    }

    fun isSuccess(): Boolean {
        when (resultCode == 0) {
            true -> return true
            false -> return false
        }
    }
}