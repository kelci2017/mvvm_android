package com.kelci.familynote.view.Settings

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.annotation.Nullable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import com.kelci.familynote.R
import android.widget.ArrayAdapter
import com.kelci.familynote.FamilyNoteApplication
import com.kelci.familynote.model.dataStructure.BaseResult
import com.kelci.familynote.view.Base.BaseFragment
import com.kelci.familynote.viewmodel.AddFamilyMemberViewModel
import com.kelci.familynote.viewmodel.NoteSearchViewModel


class FamilyMemberFragment : BaseFragment {

    private var rootView : View? = null
    private var listView : ListView? = null
    private var familyMemberList = ArrayList<String>()
    private lateinit var addFamilyMemberModel: AddFamilyMemberViewModel
    private lateinit var noteSearchModel: NoteSearchViewModel
    var arrayAdapter : ArrayAdapter<String>? = null
    var from : String? = ""

    constructor()

    override fun setArguments(args: Bundle?) {
        this.from = args?.getString("from")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        rootView = inflater.inflate(R.layout.fragment_familymember, container, false)
        listView = rootView?.findViewById<ListView>(R.id.familymember_list) as ListView

        if (FamilyNoteApplication.familyNoteApplication?.getKeyArraylist(FamilyNoteApplication.familyNoteApplication?.resources!!.getString(R.string.member_list)) != null) {
            val savedList = FamilyNoteApplication.familyNoteApplication?.getKeyArraylist(FamilyNoteApplication.familyNoteApplication?.resources!!.getString(R.string.member_list)) as ArrayList<String>
            familyMemberList.addAll(savedList)
        }


        arrayAdapter = ArrayAdapter<String>(
                activity!!.applicationContext,
                R.layout.family_member_item,
                familyMemberList)

        listView?.adapter = arrayAdapter

        addFamilyMemberModel = ViewModelProviders.of(this).get(AddFamilyMemberViewModel::class.java)
        noteSearchModel = ViewModelProviders.of(this).get(NoteSearchViewModel::class.java)

        observeViewModel(addFamilyMemberModel)

        return rootView
    }

    override fun onResume() {
        super.onResume()
        setListOnClickListener()
    }

    private fun setListOnClickListener() {
        listView?.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->

            val selectedItem = parent.getItemAtPosition(position)
            if (from == getMainActivity()?.resources!!.getString(R.string.settings_sender)) {
                noteSearchModel.noteSearchSender.value = selectedItem.toString()
            }
            if (from == getMainActivity()?.resources!!.getString(R.string.settings_receiver)) {
                noteSearchModel.noteSearchReceiver.value = selectedItem.toString()
            }

            activity!!.supportFragmentManager.beginTransaction().remove(this as? Fragment).commit()

        }
    }

    private fun observeViewModel(viewModel: AddFamilyMemberViewModel) {

        viewModel.addFamilyMemberResult.observe(this, object : Observer<BaseResult> {
            override fun onChanged(@Nullable addFamilyMemberResult: BaseResult?) {
                //dismissProgressDialog()
                if (addFamilyMemberResult!!.isSuccess()) {
                    familyMemberList = FamilyNoteApplication.familyNoteApplication?.getKeyArraylist(FamilyNoteApplication.familyNoteApplication?.resources!!.getString(R.string.member_list)) as ArrayList<String>
                    arrayAdapter?.notifyDataSetChanged()
                }
            }
        })
    }

//    fun getFrom(from : String) {
//        this.from = from
//    }
}