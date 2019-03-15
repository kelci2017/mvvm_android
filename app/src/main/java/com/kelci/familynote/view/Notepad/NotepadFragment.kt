package com.kelci.familynote.view.Notepad

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.kelci.familynote.R
import android.widget.TextView
import android.widget.ArrayAdapter
import com.kelci.familynote.view.Base.BaseFragment


class NotepadFragment : BaseFragment() {

    private var rootView : View? = null
    private var spinnerTo : Spinner? = null
    private var spinnerFrom : Spinner? =null
    private var sender : TextView? = null
    private var receiver : TextView? = null
    private var noteBody : TextView? = null
    private var submit : Button? = null
    private val familyMemberList = ArrayList<String>()
    private var spinnerAdapter : ArrayAdapter<String>? = null
    private var spinnerFromAdapter : ArrayAdapter<String>? = null

    companion object {

        fun newInstance(): NotepadFragment {
            return NotepadFragment()
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        rootView = inflater.inflate(R.layout.fragment_notepad, container, false)
        spinnerFrom = rootView?.findViewById(R.id.fromlist) as Spinner
        spinnerTo = rootView?.findViewById(R.id.tolist) as Spinner
        sender = rootView?.findViewById(R.id.sender) as TextView
        receiver = rootView?.findViewById(R.id.receiver) as TextView
        noteBody = rootView?.findViewById(R.id.notebody) as TextView
        submit = rootView?.findViewById(R.id.submit) as Button

        familyMemberList.add("")
        familyMemberList.add("Kelci")
        familyMemberList.add("Alisa")
        familyMemberList.add("Arwin")

        spinnerAdapter = object : ArrayAdapter<String>(activity!!.applicationContext, R.layout.spinner_item, familyMemberList) {
            override fun getDropDownView(position: Int, convertView: View?,
                                         parent: ViewGroup): View {
                val view = super.getDropDownView(position, convertView, parent)
                val tv = view as TextView
                if (position == 0) {
                    var params = tv.layoutParams
                    params.height = 10
                    tv.layoutParams = params
                } else {
                    var params = tv.layoutParams
                    params.height = 150
                    tv.layoutParams = params
                }
                return view
            }

        }
        spinnerAdapter?.setDropDownViewResource(R.layout.spinner_item)
        spinnerTo?.adapter = spinnerAdapter

        spinnerFrom?.adapter = spinnerAdapter

        setSpinnerListener()

        return rootView
    }

    private fun setSpinnerListener() {
        spinnerTo?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                if (position > 0) {
                    receiver?.text = spinnerTo?.selectedItem.toString()
                    view.visibility = View.GONE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                /*Do something if nothing selected*/
            }
        }

        spinnerFrom?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                if (position > 0) {
                    sender?.text = spinnerFrom?.selectedItem.toString()
                    view.visibility = View.GONE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                /*Do something if nothing selected*/
            }
        }
    }
}