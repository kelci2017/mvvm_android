package com.kelci.familynote.view

import android.os.Bundle
import android.support.v4.app.Fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.kelci.familynote.R
import android.content.ClipData.Item



class SettingsFragment : Fragment() {

    private var rootView : View? = null
    private var listView : ListView? = null

    companion object {

        fun newInstance(): SettingsFragment {
            return SettingsFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        rootView = inflater?.inflate(R.layout.fragment_settings, container, false)
        listView = rootView?.findViewById<ListView>(R.id.settings_list) as ListView

        val settingsList = ArrayList<Item>()
        // Header
        settingsList.add(SettingsSection("Settings") as Item)
        // State Name
        settingsList.add(SettingsItem(getString(R.string.settings_sender), getString(R.string.settings_default))as Item)
        settingsList.add(SettingsItem(getString(R.string.settings_receiver), getString(R.string.settings_default)) as Item)
        settingsList.add(SettingsItem(getString(R.string.settings_date), getString(R.string.settings_default_date)) as Item)
        settingsList.add(SettingsItem(getString(R.string.settings_add_member), "") as Item)
        settingsList.add(SettingsItem(getString(R.string.settings_logout), "") as Item)

        // Header
        settingsList.add(SettingsSection("About") as Item)
        // State Name
        settingsList.add(SettingsItem(getString(R.string.settings_version_title), getString(R.string.settings_version)) as Item)

        return rootView
    }
}