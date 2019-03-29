package com.kelci.familynote.view.noteboard

class NoteItem(from : String, to : String, date : String, note : String) {

    private var from : String = ""
    private var to : String = ""
    private var date : String = ""
    private var note : String = ""

    init {
        this.from = from
        this.to = to
        this.date = date
        this.note = note
    }

    fun getSender(): String {
        return from
    }

    fun getReceiver(): String {
        return to
    }

    fun getDate(): String {
        return date
    }

    fun getNote(): String {
        return note
    }

}