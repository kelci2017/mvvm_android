package com.kelci.familynote.model.dataStructure


class Note(fromWhom : String, toWhom : String, noteBody : String, created : String, userID : String?) {

    private var fromWhom : String = ""
    private var toWhom : String = ""
    private var noteBody : String = ""
    private var created : String = ""
    private var userID : String? = null

    init {
        this.fromWhom = fromWhom
        this.toWhom = toWhom
        this.noteBody = noteBody
        this.created = created
        this.userID = userID
    }

    fun getFromWhom() : String {
        return fromWhom
    }

    fun getToWhom() : String {
        return toWhom
    }

    fun getNotebody() : String {
        return noteBody
    }

    fun getCreated() : String {
        return created
    }
}