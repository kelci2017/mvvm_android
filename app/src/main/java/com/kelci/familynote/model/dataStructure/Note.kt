package com.kelci.familynote.model.dataStructure


class Note(from : String, to : String, noteBody : String, date : String) {

    private var from : String = ""
    private var to : String = ""
    private var noteBody : String = ""
    private var date : String = ""
//    val calendarF = Calendar.getInstance()
//    var day = calendarF.get(Calendar.DAY_OF_MONTH)
//    var year = calendarF.get(Calendar.YEAR)
//    var month = calendarF.get(Calendar.MONTH)
//
//    private var dateS : String = "" + year + "-0" + (month + 1) + "-" + day
//    private var dateL : String = "" + year + (month + 1) + "-" + day

    init {
        this.from = from
        this.to = to
        this.noteBody = noteBody
        this.date = date
    }

    fun getSender() : String {
        return from
    }

    fun getReceiver() : String {
        return to
    }

    fun getNotebody() : String {
        return noteBody
    }

    fun getDate() : String {
        return date
    }
}