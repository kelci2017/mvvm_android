package com.kelci.familynote.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import com.fasterxml.jackson.databind.ser.Serializers
import com.kelci.familynote.R

class AddFamilyMemberFragment : BaseFragment() {

    private var rootView : View? = null

    companion object {

        fun newInstance(): AddFamilyMemberFragment {
            return newInstance()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        rootView = inflater?.inflate(R.layout.fragment_addfamilymember, container, false)

        return rootView
    }
}