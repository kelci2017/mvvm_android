package com.kelci.familynote.view.settings

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.FragmentActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.kelci.familynote.FamilyNoteApplication
import com.kelci.familynote.R
import com.kelci.familynote.model.dataStructure.BaseResult
import com.kelci.familynote.view.base.BaseFragment
import com.kelci.familynote.view.base.RootActivity
import com.kelci.familynote.viewmodel.AddFamilyMemberViewModel
import kotlinx.android.synthetic.main.fragment_addfamilymember.*

class AddFamilyMemberFragment : BaseFragment() {

    private var rootView : View? = null
    private var savedFamilyMemberList = ArrayList<String>()
    private var newAddedFamilyMemberList = ArrayList<String>()

    private lateinit var addFamilyMemberModel : AddFamilyMemberViewModel
    private var add_button : Button? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        rootView = inflater.inflate(R.layout.fragment_addfamilymember, container, false)
        add_button = rootView?.findViewById(R.id.add_button) as Button

        addFamilyMemberModel = ViewModelProviders.of(getMainActivity() as FragmentActivity).get(AddFamilyMemberViewModel::class.java)

        if (FamilyNoteApplication.familyNoteApplication?.getKeyArraylist(FamilyNoteApplication.familyNoteApplication?.resources!!.getString(R.string.member_list)) != null) {
            savedFamilyMemberList = FamilyNoteApplication.familyNoteApplication?.getKeyArraylist(FamilyNoteApplication.familyNoteApplication?.resources!!.getString(R.string.member_list)) as ArrayList<String>
        }

        add_button?.setOnClickListener { view ->
            if (FamilyNoteApplication.familyNoteApplication!!.isInternetAvailable()) {
                add()
            } else {
                getMainActivity()?.showNetworkError()
            }
        }

        observeViewModel(addFamilyMemberModel)
        return rootView
    }

    private fun add() {

        addFamilyMember(name_first?.text.toString())
        addFamilyMember(name_second?.text.toString())
        addFamilyMember(name_third?.text.toString())
        addFamilyMember(name_fourth?.text.toString())
        addFamilyMember(name_fifth?.text.toString())
        addFamilyMember(name_sixth?.text.toString())
        addFamilyMember(name_seventh?.text.toString())
        addFamilyMember(name_eighth?.text.toString())
        addFamilyMember(name_ninth?.text.toString())

        if (newAddedFamilyMemberList.size > 0) {
            showProgressDialog("Adding family members...")
            newAddedFamilyMemberList.addAll(savedFamilyMemberList)
            addFamilyMemberModel.addFamilyMmeber(newAddedFamilyMemberList)
        } else {
            activity!!.supportFragmentManager.beginTransaction().remove(this@AddFamilyMemberFragment).commit()
            getMainActivity()?.onBackPressed()
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
                if (addedResult?.resultCode == TimeoutError) {
                    getMainActivity()?.showLoginActivity(getMainActivity() as RootActivity)
                }
                if (addedResult?.resultCode == 21) {
                    FamilyNoteApplication.familyNoteApplication?.putKeyValue(resources.getString(R.string.token), null)
                    add()
                    return
                }
                if (addedResult!!.isSuccess()) {
                    dismissProgressDialog()
                    activity!!.supportFragmentManager.beginTransaction().remove(this@AddFamilyMemberFragment).commit()
                    getMainActivity()?.onBackPressed()
                } else {
                    getMainActivity()?.errorHandler(addedResult.resultDesc.toString(), "Add family members failed!")
                }
            }
        })
    }
}