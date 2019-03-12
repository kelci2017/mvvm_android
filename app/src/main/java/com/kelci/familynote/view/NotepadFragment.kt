package com.kelci.familynote.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kelci.familynote.R

class NotepadFragment : BaseFragment() {

    companion object {

        fun newInstance(): NotepadFragment {
            return NotepadFragment()
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater?.inflate(R.layout.fragment_notepad, container, false)
    }
}