package com.kelci.familynote.model.dataStructure


class Note(fromWhom : String, toWhom : String, noteBody : String, created : String, userID : String?, _id : String?, __v : String?) {

    private var fromWhom : String = ""
    private var toWhom : String = ""
    private var noteBody : String = ""
    private var created : String = ""
    private var _id : String? = null
    private var __v : String? = null
    private var userID : String? = null

    init {
        this.fromWhom = fromWhom
        this.toWhom = toWhom
        this.noteBody = noteBody
        this.created = created
        this.userID = userID
        this._id = _id
        this.__v = __v
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