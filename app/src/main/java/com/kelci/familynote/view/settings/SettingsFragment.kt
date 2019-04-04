package com.kelci.familynote.view.settings

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import com.kelci.familynote.FamilyNoteApplication
import com.kelci.familynote.R
import com.kelci.familynote.model.dataStructure.BaseResult
import com.kelci.familynote.view.base.BaseFragment
import com.kelci.familynote.view.base.RootActivity
import com.kelci.familynote.viewmodel.AddFamilyMemberViewModel
import com.kelci.familynote.viewmodel.LogoutViewModel
import com.kelci.familynote.viewmodel.NoteSearchViewModel


class SettingsFragment : BaseFragment() {

    private var rootView : View? = null
    private var settingListView : ListView? = null
    private var settingsAdapter : SettingsAdapter? = null
    private val settingsList = ArrayList<Item>()
    private lateinit var logoutModel: LogoutViewModel
    private lateinit var noteSearchModel: NoteSearchViewModel
    private lateinit var addFamilyMemberModel: AddFamilyMemberViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        rootView = inflater.inflate(R.layout.fragment_settings, container, false)
        settingListView = rootView?.findViewById(R.id.settings_list) as ListView

        logoutModel = ViewModelProviders.of(getMainActivity() as FragmentActivity).get(LogoutViewModel::class.java)
        noteSearchModel = ViewModelProviders.of(getMainActivity() as FragmentActivity).get(NoteSearchViewModel::class.java)
        addFamilyMemberModel = ViewModelProviders.of(getMainActivity() as FragmentActivity).get(AddFamilyMemberViewModel::class.java)

        observeViewModel()

        listAndAdapterSetup()

        return rootView
    }

    override fun onResume() {
        super.onResume()
        settingListView?.isEnabled = true
        setListOnClickListener()
    }

    private fun listAndAdapterSetup() {
        settingsList.clear()

        settingsList.add(SettingsSection("Settings") as Item)
        settingsList.add(SettingsItem(getString(R.string.settings_sender), noteSearchModel.noteSearchSender.value!!) as Item)
        settingsList.add(SettingsItem(getString(R.string.settings_receiver), noteSearchModel.noteSearchReceiver.value!!) as Item)
        settingsList.add(SettingsItem(getString(R.string.settings_date), noteSearchModel.noteSearchDate.value!!) as Item)
        settingsList.add(SettingsItem(getString(R.string.settings_add_member), "") as Item)
        settingsList.add(SettingsItem(getString(R.string.settings_logout), "") as Item)

        settingsList.add(SettingsSection(getString(R.string.about_name)) as Item)
        settingsList.add(SettingsItem(getString(R.string.settings_version_title), getString(R.string.settings_version)) as Item)
        settingsAdapter = SettingsAdapter(this, settingsList)
        settingListView?.adapter = settingsAdapter
        settingListView?.divider = null
    }
    private fun setListOnClickListener() {

        settingListView?.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->

            val selectedItem = parent.getItemAtPosition(position) as Item
                when(selectedItem.getTitle()) {
                    getString(R.string.settings_sender) -> {
                        //check notes from
                        showFamilyMemberFragment(getMainActivity()?.resources!!.getString(R.string.settings_sender))

                    }
                    getString(R.string.settings_receiver) -> {
                        //check notes to
                        showFamilyMemberFragment(getMainActivity()?.resources!!.getString(R.string.settings_receiver))
                    }
                    getString(R.string.settings_date) -> {
                        //check notes date
                        Log.i("SettingsFragment", "The settinglist count is: " + settingsList.count().toString())
                        if (settingsList.count() > 8) {
                            settingsList.removeAt(4)
                        } else {
                            settingsList.add(4, SettingsItem("", "") as Item)
                        }
                        settingsAdapter?.notifyDataSetChanged()
                    }
                    getString(R.string.settings_add_member) -> {
                        //add family members
                        settingListView?.isEnabled = false
                        showAddFamilyMemberFragment()
                    }
                    getString(R.string.settings_logout) -> {
                        //logout
                        if (FamilyNoteApplication.familyNoteApplication!!.isInternetAvailable()) {
                            logoutModel.logout()
                            getMainActivity()?.showProgressDialog("Logout...")
                        } else {
                            getMainActivity()?.showNetworkError()
                        }
                    }
            else -> {

                }
            }
        }
    }

    private fun showFamilyMemberFragment(from : String) {

        var familyMemberFragment = FamilyMemberFragment() as Fragment
        val bundle = Bundle()
        bundle.putString("from", from)
        familyMemberFragment.arguments = bundle
        val fragmentManager = activity!!.supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.add(R.id.settings_layout, familyMemberFragment, "familyMemberFragment")
        fragmentTransaction.addToBackStack(getMainActivity()?.resources!!.getString(R.string.familyMemberFragment))
        fragmentTransaction.commit()
    }

    private fun showAddFamilyMemberFragment() {

        var addFamilyMemberFragment = AddFamilyMemberFragment() as Fragment
        val fragmentManager = activity!!.supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.add(R.id.settings_layout, addFamilyMemberFragment, "addFamilyMemberFragment")
        fragmentTransaction.addToBackStack(getMainActivity()?.resources!!.getString(R.string.addFamilyMemberFragment))
        fragmentTransaction.commit()

    }

    private fun observeViewModel() {
        observeLogoutViewModel(logoutModel)
        observeFilterViewModel(noteSearchModel)
        observeAddFamilyMemberViewModel(addFamilyMemberModel)
    }

    private fun observeLogoutViewModel(viewModel : LogoutViewModel) {

        viewModel.logoutResult.observe(this, object : Observer<BaseResult> {
            override fun onChanged(@Nullable baseResult: BaseResult?) {
                dismissProgressDialog()
                if (baseResult!!.isSuccess() || baseResult.resultCode == TimeoutError) {
                    //save the username and password for autologin
                    FamilyNoteApplication.familyNoteApplication?.putKeyValue(resources.getString(R.string.token), null)
                    FamilyNoteApplication.familyNoteApplication?.putKeyValue(resources.getString(R.string.sessionid), null)

                    getMainActivity()?.showLoginActivity(getMainActivity() as RootActivity)
                } else if (baseResult.resultCode == 21) {
                    FamilyNoteApplication.familyNoteApplication?.putKeyValue(resources.getString(R.string.token), null)
                    logoutModel.logout()
                    return
                } else {
                    getMainActivity()?.errorHandler(baseResult.resultDesc.toString(), "Logout failed!")
                }
            }
        })
    }

    private fun observeFilterViewModel(viewModel : NoteSearchViewModel) {

        viewModel.noteSearchDate.observe(getMainActivity() as FragmentActivity, object : Observer<String> {
            override fun onChanged(date: String?) {
                doFilter()
            }
        })
        viewModel.noteSearchSender.observe(getMainActivity() as FragmentActivity, object : Observer<String> {
            override fun onChanged(sender: String?) {
                doFilter()
            }
        })
        viewModel.noteSearchReceiver.observe(getMainActivity() as FragmentActivity, object : Observer<String> {
            override fun onChanged(receiver: String?) {
                doFilter()
            }
        })

        viewModel.noteSearchResult.observe(getMainActivity() as FragmentActivity,  object : Observer<BaseResult> {
            override fun onChanged(@Nullable baseResult: BaseResult?) {
                if (baseResult?.resultCode == 21) {
                    FamilyNoteApplication.familyNoteApplication?.putKeyValue(resources.getString(R.string.token), null)
                    //noteSearchModel.filterNote(settingsAdapter?.getSenderName()!!, settingsAdapter?.getReveiverName()!!, settingsAdapter?.getDate()!!)
                    return
                }
                dismissProgressDialog()
                if (baseResult?.resultCode == TimeoutError) {
                    getMainActivity()?.showLoginActivity(getMainActivity() as RootActivity)
                }
                if (!baseResult!!.isSuccess()) {
                    getMainActivity()?.errorHandler(baseResult.resultDesc.toString(), "Filter failed!")
                }
            }
        })
    }

    private fun observeAddFamilyMemberViewModel(viewModel : AddFamilyMemberViewModel) {
        viewModel.addFamilyMemberResult.observe(this,  object : Observer<BaseResult> {
            override fun onChanged(@Nullable baseResult: BaseResult?) {
                if (baseResult!!.isSuccess()) {
                    settingListView?.isEnabled = true
                }
            }
        })
    }

    fun setSelectedDate(selectedDate : String) {
        noteSearchModel.noteSearchDate.value = selectedDate
    }

    private fun doFilter() {
        listAndAdapterSetup()
        if (FamilyNoteApplication.familyNoteApplication!!.isInternetAvailable()) {
            noteSearchModel.filterNote(noteSearchModel.noteSearchSender.value!!, noteSearchModel.noteSearchReceiver.value!!, noteSearchModel.noteSearchDate.value!!)
            getMainActivity()?.showProgressDialog("Filtering...")
        } else {
            getMainActivity()?.showNetworkError()
        }
    }
}