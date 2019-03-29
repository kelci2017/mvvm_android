package com.kelci.familynote.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.kelci.familynote.FamilyNoteApplication
import com.kelci.familynote.R
import com.kelci.familynote.model.dataStructure.BaseResult
import com.kelci.familynote.model.restService.ServiceUtil
import com.kelci.familynote.viewmodel.base.BaseViewModel
import restclient.RestHandler
import restclient.RestParms
import restclient.RestResult
import restclient.RestTag

class NoteSubmiteViewModel  : BaseViewModel() {

    var submitNoteResult = MutableLiveData<BaseResult>()
    var sender : String = ""
    var receiver : String = ""
    var noteBody : String = ""

    fun submitNote(sender : String, receiver: String, noteBody : String) {
        this.sender = sender
        this.receiver = receiver
        this.noteBody = noteBody

        if (FamilyNoteApplication.familyNoteApplication?.getKeyValue(FamilyNoteApplication.familyNoteApplication?.resources!!.getString(R.string.token)) != null) {
            callSubmitNote(sender, receiver, noteBody)
        } else {
            getToken()
        }
    }

    override fun callNext() {

        callSubmitNote(sender, receiver, noteBody)

    }
    private fun callSubmitNote(sender : String, receiver: String, noteBody : String) {

        var restHandler : RestHandler<BaseResult>? = null
        restHandler as RestHandler<Any>?

        var restParams : RestParms = RestParms()
        val senderKey = FamilyNoteApplication.familyNoteApplication?.resources!!.getString(R.string.sender)
        val receiverKey = FamilyNoteApplication.familyNoteApplication?.resources!!.getString(R.string.receiver)
        val noteBodyKey = FamilyNoteApplication.familyNoteApplication?.resources!!.getString(R.string.note_body)
        restParams.setParams(senderKey, sender, receiverKey, receiver, noteBodyKey, noteBody)

        var restTag = RestTag()
        restTag.tag = "NoteSubmit"

        ServiceUtil.submitNote(restTag,restParams,object : RestHandler<Any>(){
            override fun onReturn(result: RestResult<Any>?) {

                val baseResult : BaseResult? = result?.resultObject as? BaseResult

                if (baseResult != null) {
                    submitNoteResult.value = baseResult
                }
            }
        }, false)
    }
}