package com.kelci.familynote.view

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class SettingsPagerAdapter(fm : FragmentManager, numOfTabs : Int) : FragmentStatePagerAdapter(fm) {

    private var numOfTabs : Int = 0

    init {
        this.numOfTabs = numOfTabs
    }

    override fun getItem(position: Int): Fragment {
        when(position) {
            0 -> {
                 var noteboardFragment = NoteboardFragment() as Fragment
                return noteboardFragment
            }
            1 -> {
                var notepadFragment = NotepadFragment() as Fragment
                return notepadFragment
            }
            2 -> {
                var settingsFragment = SettingsFragment() as Fragment
                return settingsFragment
            } else -> {
            return Fragment()
        }
        }
    }

    override fun getCount(): Int {
        return numOfTabs
    }
}