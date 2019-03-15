package com.kelci.familynote.view.Settings

import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import com.kelci.familynote.R
import android.widget.ArrayAdapter
import com.kelci.familynote.view.Base.BaseFragment


class FamilyMemberFragment: BaseFragment() {

    private var rootView : View? = null
    private var listView : ListView? = null
    private val familyMemberList = ArrayList<String>()

    companion object {

        fun newInstance(): FamilyMemberFragment {
            return newInstance()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        rootView = inflater?.inflate(R.layout.fragment_familymember, container, false)
        listView = rootView?.findViewById<ListView>(R.id.familymember_list) as ListView

        familyMemberList.add("Kelci")
        familyMemberList.add("Alisa")
        familyMemberList.add("Arwin")


        val arrayAdapter = ArrayAdapter<String>(
                activity!!.applicationContext,
                R.layout.family_member_item,
                familyMemberList)

        listView?.adapter = arrayAdapter

        return rootView
    }

    override fun onResume() {
        super.onResume()
        setListOnClickListener()
    }

    fun setListOnClickListener() {
        listView?.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->

            val selectedItem = parent.getItemAtPosition(position) as Item

            activity!!.supportFragmentManager.beginTransaction().remove(this as? Fragment).commit()

        }
    }
}