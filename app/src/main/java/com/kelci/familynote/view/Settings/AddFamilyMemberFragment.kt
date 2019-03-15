package com.kelci.familynote.view.Settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kelci.familynote.R
import com.kelci.familynote.view.Base.BaseFragment

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