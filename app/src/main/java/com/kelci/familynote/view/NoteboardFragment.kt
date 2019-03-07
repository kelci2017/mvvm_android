package com.kelci.familynote.view

import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kelci.familynote.R

class NoteboardFragment : Fragment() {

    companion object {

        fun newInstance(): NoteboardFragment {
            return NoteboardFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_noteboard, container, false)
    }
}