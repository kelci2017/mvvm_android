package com.kelci.familynote.view.Settings

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import com.kelci.familynote.R
import com.kelci.familynote.view.Base.BaseFragment
import kotlinx.android.synthetic.main.settings_item.view.*


class SettingsFragment : BaseFragment() {

    private var rootView : View? = null
    private var listView : ListView? = null
    private var settingsAdapter : SettingsAdapter? = null
    private val settingsList = ArrayList<Item>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        rootView = inflater?.inflate(R.layout.fragment_settings, container, false)
        listView = rootView?.findViewById(R.id.settings_list) as ListView


        // Header
        settingsList.add(SettingsSection("Settings") as Item)
        // Items
        settingsList.add(SettingsItem(getString(R.string.settings_sender), getString(R.string.settings_default)) as Item)
        settingsList.add(SettingsItem(getString(R.string.settings_receiver), getString(R.string.settings_default)) as Item)
        settingsList.add(SettingsItem(getString(R.string.settings_date), getString(R.string.settings_default_date)) as Item)
        settingsList.add(SettingsItem(getString(R.string.settings_add_member), "") as Item)
        settingsList.add(SettingsItem(getString(R.string.settings_logout), "") as Item)

        // Header
        settingsList.add(SettingsSection(getString(R.string.about_name)) as Item)
        // Items
        settingsList.add(SettingsItem(getString(R.string.settings_version_title), getString(R.string.settings_version)) as Item)
        settingsAdapter = SettingsAdapter(activity!!.applicationContext, settingsList)
        listView?.adapter = settingsAdapter
        //setListOnClickListener()
        listView?.divider = null
        return rootView
    }

    override fun onResume() {
        super.onResume()
        listView?.isEnabled = true
        setListOnClickListener()
    }

    private fun setListOnClickListener() {

        listView?.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->

            val calendarView = view.calendarView

           //settingsAdapter?.selectDate(calendarView)

            val selectedItem = parent.getItemAtPosition(position) as Item
                when(selectedItem.getTitle()) {
                    getString(R.string.settings_sender) -> {
                        //check notes from
                        showFamilyMemberFragment()

                    }
                    getString(R.string.settings_receiver) -> {
                        //check notes to
                        showFamilyMemberFragment()
                    }
                    getString(R.string.settings_date) -> {
                        //check notes date
                        Log.i("SettingsFragment", "The settinglist count is: " + settingsList.count().toString())
                        if (settingsList.count() > 8) {
                            settingsList.removeAt(4)
                            //settingsAdapter?.showDivider()
                        } else {
                            settingsList.add(4, SettingsItem("", "") as Item)
                            //settingsAdapter?.hideDivider()
                        }
                        settingsAdapter?.notifyDataSetChanged()
                      //settingsAdapter?.expandClapseDate()
                    }
                    getString(R.string.settings_add_member) -> {
                        //add family members
                        listView?.isEnabled = false
                        showAddFamilyMemberFragment()
                    }
                    getString(R.string.settings_logout) -> {
                        //logout

                    }
//                    "" -> {
//                        //the added calendar view
//                        settingsAdapter?.selectDate()
//                        settingsAdapter?.notifyDataSetChanged()
                //}
            else -> {

                }
            }
        }
    }

    private fun showFamilyMemberFragment() {

        var familyMemberFragment = FamilyMemberFragment() as Fragment
        val fragmentManager = activity!!.supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.add(R.id.settings_layout, familyMemberFragment, "familyMemberFragment")
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    private fun showAddFamilyMemberFragment() {

        var addFamilyMemberFragment = AddFamilyMemberFragment() as Fragment
        val fragmentManager = activity!!.supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.add(R.id.settings_layout, addFamilyMemberFragment, "addFamilyMemberFragment")
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

    }
}