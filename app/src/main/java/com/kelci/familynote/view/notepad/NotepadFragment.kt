package com.kelci.familynote.view.notepad

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.FragmentActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.kelci.familynote.R
import android.widget.TextView
import android.widget.ArrayAdapter
import com.kelci.familynote.FamilyNoteApplication
import com.kelci.familynote.utilities.CommonUtil
import com.kelci.familynote.model.dataStructure.BaseResult
import com.kelci.familynote.view.base.BaseFragment
import com.kelci.familynote.view.base.RootActivity
import com.kelci.familynote.viewmodel.AddFamilyMemberViewModel
import com.kelci.familynote.viewmodel.NoteSearchViewModel
import com.kelci.familynote.viewmodel.NoteSubmiteViewModel
import kotlinx.android.synthetic.main.fragment_notepad.*


class NotepadFragment : BaseFragment() {

    private var rootView : View? = null
    private var tolist : Spinner? = null
    private var fromlist : Spinner? =null
    private var submit : Button? = null
    private var familyMemberList = ArrayList<String>()
    private var spinnerAdapter : ArrayAdapter<String>? = null
    private var firstEmptyMember = ArrayList<String>()

    private lateinit var noteSubmitModel: NoteSubmiteViewModel
    private lateinit var addFamilyMemberModel: AddFamilyMemberViewModel
    private lateinit var noteSearchModel: NoteSearchViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        familyMemberList.clear()
        rootView = inflater.inflate(R.layout.fragment_notepad, container, false)
        fromlist = rootView?.findViewById(R.id.fromlist) as Spinner
        tolist = rootView?.findViewById(R.id.tolist) as Spinner
        submit = rootView?.findViewById(R.id.submit) as Button

        firstEmptyMember.add("")
        familyMemberList.addAll(firstEmptyMember)

        if (FamilyNoteApplication.familyNoteApplication?.getKeyArraylist(FamilyNoteApplication.familyNoteApplication?.resources!!.getString(R.string.member_list)) != null) {
            val savedList = FamilyNoteApplication.familyNoteApplication?.getKeyArraylist(FamilyNoteApplication.familyNoteApplication?.resources!!.getString(R.string.member_list)) as ArrayList<String>
            familyMemberList.addAll(savedList)
        }

        if (familyMemberList.count() <= 1) {
            fromlist?.visibility = View.GONE
            tolist?.visibility = View.GONE
        } else {
            fromlist?.visibility = View.VISIBLE
            tolist?.visibility = View.VISIBLE
        }

        setListAndAdapter()
        setSpinnerListener()

        noteSubmitModel = ViewModelProviders.of(getMainActivity() as FragmentActivity).get(NoteSubmiteViewModel::class.java)
        addFamilyMemberModel = ViewModelProviders.of(getMainActivity() as FragmentActivity).get(AddFamilyMemberViewModel::class.java)
        noteSearchModel = ViewModelProviders.of(getMainActivity() as FragmentActivity).get(NoteSearchViewModel::class.java)

        observeViewModel()

        submit?.setOnClickListener { view ->

            if (notebody.text == null) showAlertBox("Please input your note.", "Note is empty!")

            noteSubmitModel.submitNote(sender.text.toString(), receiver.text.toString(),notebody.text.toString())
            showProgressDialog("Loading...")

        }

        return rootView
    }

    private fun setSpinnerListener() {

        tolist?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position > 0) {
                    receiver?.setText(tolist?.selectedItem.toString())
                    view?.visibility = View.GONE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                /*Do something if nothing selected*/
            }
        }
        fromlist?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if (position > 0) {
                    sender?.setText(fromlist?.selectedItem.toString())
                    view?.visibility = View.GONE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                /*Do something if nothing selected*/
            }
        }
    }

    private fun observeViewModel() {
        observeFamilyMemberModel(addFamilyMemberModel)
        observeSubmitNoteModel(noteSubmitModel)
    }

    private fun observeSubmitNoteModel(viewModel: NoteSubmiteViewModel) {

        viewModel.submitNoteResult.observe(this, object : Observer<BaseResult> {
            override fun onChanged(@Nullable submitResult: BaseResult?) {
                dismissProgressDialog()
                if (submitResult?.resultCode == TimeoutError) {
                    getMainActivity()?.showLoginActivity(getMainActivity() as RootActivity)
                    return
                }
                if (submitResult?.resultCode == 21) {
                    FamilyNoteApplication.familyNoteApplication?.putKeyValue(resources.getString(R.string.token), null)
                    noteSubmitModel.submitNote(sender.text.toString(), receiver.text.toString(),notebody.text.toString())
                    return
                }
                if (submitResult!!.isSuccess()) {
                    dismissProgressDialog()
                    showAlertBox("Note was submitted successfully.","Submitted")

                    val isToday = (noteSearchModel.noteSearchDate.value == getMainActivity()?.resources!!.getString(R.string.settings_default_date) || noteSearchModel.noteSearchDate.value == CommonUtil.getTodayDate())
                    val sameSender = (noteSearchModel.noteSearchSender.value?.toLowerCase() == sender?.text.toString().toLowerCase() || getMainActivity()?.resources!!.getString(R.string.settings_default) == noteSearchModel.noteSearchSender.value)
                    val sameReceiver = (noteSearchModel.noteSearchReceiver.value?.toLowerCase() == receiver?.text.toString().toLowerCase() || getMainActivity()?.resources!!.getString(R.string.settings_default) == noteSearchModel.noteSearchReceiver.value)

                    if (isToday && sameSender && sameReceiver) {
                        noteSearchModel.filterNote(noteSearchModel.noteSearchSender.value!!, noteSearchModel.noteSearchReceiver.value!!, CommonUtil.getTodayDate())
                    }

                    sender?.setText("")
                    receiver?.setText("")
                    notebody?.setText("")
                } else {
                    getMainActivity()?.errorHandler(submitResult.resultDesc.toString(), "Submit failed!")
                }
            }
        })
    }

    private fun observeFamilyMemberModel(viewModel: AddFamilyMemberViewModel) {

        viewModel.addFamilyMemberResult.observe(this, object : Observer<BaseResult> {
            override fun onChanged(@Nullable addFamilyMemberResult: BaseResult?) {
                //dismissProgressDialog()
                if (addFamilyMemberResult!!.isSuccess()) {
                    val familyMemberListNew = FamilyNoteApplication.familyNoteApplication?.getKeyArraylist(FamilyNoteApplication.familyNoteApplication?.resources!!.getString(R.string.member_list)) as ArrayList<String>
                    familyMemberList.clear()
                    familyMemberList.addAll(firstEmptyMember)
                    familyMemberList.addAll(familyMemberListNew)
                    setListAndAdapter()
                    setSpinnerListener()
                    spinnerAdapter?.notifyDataSetChanged()
                    if (familyMemberList.count() > 1) {
                        fromlist?.visibility = View.VISIBLE
                        tolist?.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun setListAndAdapter() {

        spinnerAdapter = object : ArrayAdapter<String>(activity!!.applicationContext, R.layout.spinner_item, familyMemberList) {
            override fun getDropDownView(position: Int, convertView: View?,
                                         parent: ViewGroup): View {
                val view = super.getDropDownView(position, convertView, parent)
                val tv = view as TextView
                if (position == 0) {
                    val params = tv.layoutParams
                    params.height = 10
                    tv.layoutParams = params
                } else {
                    val params = tv.layoutParams
                    params.height = 150
                    tv.layoutParams = params
                }
                return view
            }

        }
        spinnerAdapter?.setDropDownViewResource(R.layout.spinner_item)
        tolist?.adapter = spinnerAdapter

        fromlist?.adapter = spinnerAdapter
    }
}