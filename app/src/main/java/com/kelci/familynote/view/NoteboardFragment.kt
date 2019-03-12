package com.kelci.familynote.view

import android.support.v4.app.Fragment
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import com.kelci.familynote.R

class NoteboardFragment : BaseFragment() {

    private var radioGroup : RadioGroup? = null
    private var rootView : View? = null

    companion object {

        fun newInstance(): NoteboardFragment {
            return NoteboardFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        rootView = inflater.inflate(R.layout.fragment_noteboard, container, false)
        radioGroup = rootView?.findViewById<RadioGroup>(R.id.radiogroup)
        return rootView
    }

    override fun onResume() {
        super.onResume()
        radioGroup?.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            // This will get the radiobutton that has changed in its check state
            onRadioButtonClicked(group.findViewById<View>(checkedId))
        })
    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.global_search ->
                    if (checked) {
                        view.highlightColor = resources.getColor(android.R.color.holo_orange_light)
                        view.setTextColor(resources.getColor(android.R.color.holo_orange_light))
                    }
                R.id.local_search ->
                    if (checked) {
                        view.highlightColor = resources.getColor(android.R.color.holo_orange_light)
                        view.setTextColor(resources.getColor(android.R.color.holo_orange_light))
                    }
            }
        }
    }
}