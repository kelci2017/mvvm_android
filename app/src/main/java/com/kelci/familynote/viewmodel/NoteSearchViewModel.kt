package com.kelci.familynote.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.kelci.familynote.FamilyNoteApplication
import com.kelci.familynote.R
import com.kelci.familynote.Utilities.CommonUtil
import com.kelci.familynote.model.dataStructure.BaseResult
import com.kelci.familynote.model.restService.ServiceUtil
import com.kelci.familynote.viewmodel.base.BaseViewModel
import com.kelci.familynote.viewmodel.base.MultipleObserverLiveDate
import restClient.RestHandler
import restClient.RestParms
import restClient.RestResult

class NoteSearchViewModel : BaseViewModel() {

    var noteSearchResult = MultipleObserverLiveDate<BaseResult>()
    var noteSearchSender = MultipleObserverLiveDate<String>()
    var noteSearchReceiver = MultipleObserverLiveDate<String>()
    var noteSearchDate = MultipleObserverLiveDate<String>()
    var noteSearchKeywords : String = ""
    private var senderName : String = "All"
    private var receiverName : String = "All"
    private var date : String = CommonUtil.getTodayDate()

  init {
      noteSearchSender.value = "All"
      noteSearchReceiver.value = "All"
      noteSearchDate.value = CommonUtil.getTodayDate()
  }

    fun filterNote(sender : String, receiver : String, date : String) {

        this.senderName = sender
        this.date = date
        this.receiverName = receiver

        if (FamilyNoteApplication.familyNoteApplication?.getKeyValue(FamilyNoteApplication.familyNoteApplication?.resources!!.getString(R.string.token)) != null) {
            callFilterNote(sender, receiver, date)
        } else {
            getToken()
        }
    }

    fun searchNote(keywords : String) {
         this.noteSearchKeywords = keywords

        if (FamilyNoteApplication.familyNoteApplication?.getKeyValue(FamilyNoteApplication.familyNoteApplication?.resources!!.getString(R.string.token)) != null) {
            callSearchNote(keywords)
        } else {
            getToken()
        }
    }



    override fun callNext() {

        callFilterNote(senderName, receiverName, date)

    }
    private fun callFilterNote(sender : String?, receiver : String?, date : String?) {

        var restHandler : RestHandler<BaseResult>? = null
        restHandler as RestHandler<Any>?

        var restParams : RestParms = RestParms()
        val senderKey = FamilyNoteApplication.familyNoteApplication?.resources!!.getString(R.string.sender)
        val receiverKey = FamilyNoteApplication.familyNoteApplication?.resources!!.getString(R.string.receiver)
        val dateKey = FamilyNoteApplication.familyNoteApplication?.resources!!.getString(R.string.date)
        restParams.setParams(senderKey, sender, receiverKey, receiver, dateKey, date)

        ServiceUtil.filterNote(null,restParams,object : RestHandler<Any>(){
            override fun onReturn(result: RestResult<Any>?) {

                val baseResult : BaseResult? = result?.resultObject as? BaseResult

                if (baseResult != null) {
                    noteSearchResult.value = baseResult
                }
            }
        }, false)
    }

    private fun callSearchNote(keywords : String) {
        var restHandler : RestHandler<BaseResult>? = null
        restHandler as RestHandler<Any>?

        var restParams : RestParms = RestParms()
        val keywordsKey = FamilyNoteApplication.familyNoteApplication?.resources!!.getString(R.string.keywords)
        restParams.setParams(keywordsKey, keywords)

        ServiceUtil.searchNote(null,restParams,object : RestHandler<Any>(){
            override fun onReturn(result: RestResult<Any>?) {

                val baseResult : BaseResult? = result?.resultObject as? BaseResult

                if (baseResult != null) {
                    noteSearchResult.value = baseResult
                }
            }
        }, false)
    }
}