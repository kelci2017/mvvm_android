package com.kelci.familynote.view.Settings

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
import com.kelci.familynote.view.Base.BaseFragment
import com.kelci.familynote.view.Base.RootActivity
import com.kelci.familynote.viewmodel.AddFamilyMemberViewModel
import com.kelci.familynote.viewmodel.LogoutViewModel
import com.kelci.familynote.viewmodel.NoteSearchViewModel
import kotlinx.android.synthetic.main.settings_item.view.*


class SettingsFragment : BaseFragment() {

    private var rootView : View? = null
    private var listView : ListView? = null
    private var settingsAdapter : SettingsAdapter? = null
    private val settingsList = ArrayList<Item>()
    private lateinit var logoutModel: LogoutViewModel
    private lateinit var noteSearchModel: NoteSearchViewModel
    private lateinit var addFamilyMemberModel: AddFamilyMemberViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        rootView = inflater.inflate(R.layout.fragment_settings, container, false)
        listView = rootView?.findViewById(R.id.settings_list) as ListView

        logoutModel = ViewModelProviders.of(getMainActivity() as FragmentActivity).get(LogoutViewModel::class.java)
        noteSearchModel = ViewModelProviders.of(getMainActivity() as FragmentActivity).get(NoteSearchViewModel::class.java)
        addFamilyMemberModel = ViewModelProviders.of(getMainActivity() as FragmentActivity).get(AddFamilyMemberViewModel::class.java)

        observeViewModel()

        listAndAdapterSetup()

        return rootView
    }

    override fun onResume() {
        super.onResume()
        listView?.isEnabled = true
        setListOnClickListener()
    }

    private fun listAndAdapterSetup() {
        settingsList.clear()
        // Header
        settingsList.add(SettingsSection("Settings") as Item)
        // Items
        settingsList.add(SettingsItem(getString(R.string.settings_sender), noteSearchModel.noteSearchSender.value!!) as Item)
        settingsList.add(SettingsItem(getString(R.string.settings_receiver), noteSearchModel.noteSearchReceiver.value!!) as Item)
        settingsList.add(SettingsItem(getString(R.string.settings_date), noteSearchModel.noteSearchDate.value!!) as Item)
        settingsList.add(SettingsItem(getString(R.string.settings_add_member), "") as Item)
        settingsList.add(SettingsItem(getString(R.string.settings_logout), "") as Item)

        // Header
        settingsList.add(SettingsSection(getString(R.string.about_name)) as Item)
        // Items
        settingsList.add(SettingsItem(getString(R.string.settings_version_title), getString(R.string.settings_version)) as Item)
        settingsAdapter = SettingsAdapter(this, settingsList)
        listView?.adapter = settingsAdapter
        //setListOnClickListener()
        listView?.divider = null
    }
    private fun setListOnClickListener() {

        listView?.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->

            //val calendarView = view.calendarView

           //settingsAdapter?.selectDate(calendarView)

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
//                            noteSearchModel.filterNote(settingsAdapter?.getSenderName()!!, settingsAdapter?.getReveiverName()!!, settingsAdapter?.getDate()!!)
                        } else {
                            settingsList.add(4, SettingsItem("", "") as Item)
                            //settingsAdapter?.hideDivider()
                        }
                        settingsAdapter?.notifyDataSetChanged()
                    }
                    getString(R.string.settings_add_member) -> {
                        //add family members
                        listView?.isEnabled = false
                        showAddFamilyMemberFragment()
                    }
                    getString(R.string.settings_logout) -> {
                        //logout
                        logoutModel.logout()
                        getMainActivity()?.showProgressDialog("Logout...")
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
                if (baseResult?.getResultCode() == 21) {
                    FamilyNoteApplication.familyNoteApplication?.putKeyValue(resources.getString(R.string.token), null)
                    logoutModel.logout()
                    return
                }
                dismissProgressDialog()
                if (baseResult!!.isSuccess() || baseResult.getResultCode() == TimeoutError) {
                    //save the username and password for autologin
                    FamilyNoteApplication.familyNoteApplication?.putKeyValue(resources.getString(R.string.token), null)
                    FamilyNoteApplication.familyNoteApplication?.putKeyValue(resources.getString(R.string.sessionid), null)

                    getMainActivity()?.showLoginActivity(getMainActivity() as RootActivity)
                } else {
                    getMainActivity()?.errorHandler(baseResult.getResultDesc().toString(), "Logout failed!")
                }
            }
        })
    }

    private fun observeFilterViewModel(viewModel : NoteSearchViewModel) {

        val senderObserver = Observer<String> { sender ->
            filterByName()
        }

        val receiverObserver = Observer<String> { sender ->
            filterByName()
        }

        viewModel.noteSearchSender.observe(getMainActivity() as FragmentActivity, senderObserver)

        viewModel.noteSearchReceiver.observe(getMainActivity() as FragmentActivity, receiverObserver)

        viewModel.noteSearchResult.observe(getMainActivity() as FragmentActivity,  object : Observer<BaseResult> {
            override fun onChanged(@Nullable baseResult: BaseResult?) {
                if (baseResult?.getResultCode() == 21) {
                    FamilyNoteApplication.familyNoteApplication?.putKeyValue(resources.getString(R.string.token), null)
                    //noteSearchModel.filterNote(settingsAdapter?.getSenderName()!!, settingsAdapter?.getReveiverName()!!, settingsAdapter?.getDate()!!)
                    return
                }
                dismissProgressDialog()
                if (baseResult?.getResultCode() == TimeoutError) {
                    getMainActivity()?.showLoginActivity(getMainActivity() as RootActivity)
                }
                if (!baseResult!!.isSuccess()) {
                    getMainActivity()?.errorHandler(baseResult.getResultDesc().toString(), "Filter failed!")
                }
            }
        })
    }

    private fun observeAddFamilyMemberViewModel(viewModel : AddFamilyMemberViewModel) {
        viewModel.addFamilyMemberResult.observe(this,  object : Observer<BaseResult> {
            override fun onChanged(@Nullable baseResult: BaseResult?) {
                if (baseResult!!.isSuccess()) {
                    listView?.isEnabled = true
                }
            }
        })
    }

    fun setNoteFilter() {
        noteSearchModel.filterNote(noteSearchModel.noteSearchSender.value!!, noteSearchModel.noteSearchReceiver.value!!, noteSearchModel.noteSearchDate.value!!)
        getMainActivity()?.showProgressDialog("Filtering...")
    }

    private fun filterByName() {
        listAndAdapterSetup()
        settingsAdapter?.notifyDataSetChanged()
        noteSearchModel.filterNote(noteSearchModel.noteSearchSender.value!!, noteSearchModel.noteSearchReceiver.value!!, noteSearchModel.noteSearchDate.value!!)
        getMainActivity()?.showProgressDialog("Filtering...")
    }
}