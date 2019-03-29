package com.kelci.familynote.view.settings

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.kelci.familynote.view.noteboard.NoteboardFragment
import com.kelci.familynote.view.notepad.NotepadFragment

class SettingsPagerAdapter(fm : FragmentManager, numOfTabs : Int) : FragmentStatePagerAdapter(fm) {

    private var numOfTabs : Int = 0
    private var noteboardFragment : Fragment
    private var notepadFragment : Fragment
    private var settingsFragment : Fragment

    init {
        this.numOfTabs = numOfTabs
        noteboardFragment = NoteboardFragment()
        notepadFragment = NotepadFragment()
        settingsFragment = SettingsFragment()
    }

    override fun getItem(position: Int): Fragment {
        when(position) {
            0 -> {
                 //val noteboardFragment = NoteboardFragment() as Fragment
                return noteboardFragment
            }
            1 -> {
                //val notepadFragment = NotepadFragment() as Fragment
                return notepadFragment
            }
            2 -> {
                //val settingsFragment = SettingsFragment() as Fragment
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