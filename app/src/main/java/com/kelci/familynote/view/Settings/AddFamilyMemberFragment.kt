package com.kelci.familynote.view.Settings

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.kelci.familynote.FamilyNoteApplication
import com.kelci.familynote.R
import com.kelci.familynote.model.dataStructure.BaseResult
import com.kelci.familynote.view.Base.BaseFragment
import com.kelci.familynote.viewmodel.AddFamilyMemberViewModel

class AddFamilyMemberFragment : BaseFragment() {

    private var rootView : View? = null
    private var savedFamilyMemberList = ArrayList<String>()
    private var newAddedFamilyMemberList = ArrayList<String>()

    private lateinit var addFamilyMemberModel : AddFamilyMemberViewModel

    private var nameFirst : EditText? = null
    private var nameSecond : EditText? = null
    private var nameThird : EditText? = null
    private var nameFourth : EditText? = null
    private var nameFifth : EditText? = null
    private var nameSixth : EditText? = null
    private var nameSeventh : EditText? = null
    private var nameEighth : EditText? = null
    private var nameNinth : EditText? = null
    private var addButton : Button? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        rootView = inflater.inflate(R.layout.fragment_addfamilymember, container, false)
        nameFirst = rootView?.findViewById(R.id.name_first) as EditText
        nameSecond = rootView?.findViewById(R.id.name_second) as EditText
        nameThird = rootView?.findViewById(R.id.name_third) as EditText
        nameFourth = rootView?.findViewById(R.id.name_fourth) as EditText
        nameFifth = rootView?.findViewById(R.id.name_fifth) as EditText
        nameSixth = rootView?.findViewById(R.id.name_sixth) as EditText
        nameSeventh = rootView?.findViewById(R.id.name_seventh) as EditText
        nameEighth = rootView?.findViewById(R.id.name_eighth) as EditText
        nameNinth = rootView?.findViewById(R.id.name_ninth) as EditText
        addButton = rootView?.findViewById(R.id.add_button) as Button

        addFamilyMemberModel = ViewModelProviders.of(this).get(AddFamilyMemberViewModel::class.java)

        if (FamilyNoteApplication.familyNoteApplication?.getKeyArraylist(FamilyNoteApplication.familyNoteApplication?.resources!!.getString(R.string.member_list)) != null) {
            savedFamilyMemberList = FamilyNoteApplication.familyNoteApplication?.getKeyArraylist(FamilyNoteApplication.familyNoteApplication?.resources!!.getString(R.string.member_list)) as ArrayList<String>
        }

        addButton?.setOnClickListener { view ->
            add()
            showProgressDialog("Adding family members...")
        }

        observeViewModel(addFamilyMemberModel)
        return rootView
    }

    private fun add() {

        addFamilyMember(nameFirst?.text.toString())
        addFamilyMember(nameSecond?.text.toString())
        addFamilyMember(nameThird?.text.toString())
        addFamilyMember(nameFourth?.text.toString())
        addFamilyMember(nameFifth?.text.toString())
        addFamilyMember(nameSixth?.text.toString())
        addFamilyMember(nameSeventh?.text.toString())
        addFamilyMember(nameEighth?.text.toString())
        addFamilyMember(nameNinth?.text.toString())

        if (newAddedFamilyMemberList.size > 0) {
            newAddedFamilyMemberList.addAll(savedFamilyMemberList)
            addFamilyMemberModel.addFamilyMmeber(newAddedFamilyMemberList)
        }

    }

    private fun addFamilyMember(name: String?) {
        if (name == null) return

        val trimedName = name.trim()
        if (trimedName == "") return

        if (savedFamilyMemberList.contains(trimedName)) return

        newAddedFamilyMemberList.add(trimedName)
    }

    private fun observeViewModel(viewModel : AddFamilyMemberViewModel) {
        viewModel.addFamilyMemberResult.observe(this, object : Observer<BaseResult> {
            override fun onChanged(@Nullable addedResult: BaseResult?) {
                dismissProgressDialog()
                if (addedResult!!.isSuccess()) {
                    dismissProgressDialog()
                    activity!!.supportFragmentManager.beginTransaction().remove(this@AddFamilyMemberFragment).commit()
                } else {
                    getMainActivity()?.errorHandler(addedResult.getResultDesc().toString(), "Add family members failed!")
                }
            }
        })
    }
}