package com.kelci.familynote.view.noteboard

import android.app.SearchManager
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.SearchView
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.widget.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kelci.familynote.FamilyNoteApplication
import com.kelci.familynote.R
import com.kelci.familynote.model.dataStructure.BaseResult
import com.kelci.familynote.model.dataStructure.Note
import com.kelci.familynote.view.base.BaseFragment
import com.kelci.familynote.view.base.RootActivity
import com.kelci.familynote.viewmodel.NoteSearchViewModel
import kotlinx.android.synthetic.main.fragment_noteboard.*

class NoteboardFragment : BaseFragment() {

    private var rootView : View? = null
    private var noteList = ArrayList<Note>()
    private var noteAdapter : NoteAdapter? = null
    private var searchItem: MenuItem? = null
    private var searchView: SearchView? = null
    private var searchEditText: EditText? = null
    private lateinit var noteSearchModel: NoteSearchViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        rootView = inflater.inflate(R.layout.fragment_noteboard, container, false)

        setHasOptionsMenu(true)

        noteSearchModel = ViewModelProviders.of(getMainActivity() as FragmentActivity).get(NoteSearchViewModel::class.java)
        observeViewModel(noteSearchModel)

        return rootView
    }

    override fun onResume() {
        super.onResume()
        if (FamilyNoteApplication.familyNoteApplication!!.isInternetAvailable()) {
            noteSearchModel.filterNote(noteSearchModel.noteSearchSender.value!!, noteSearchModel.noteSearchReceiver.value!!, noteSearchModel.noteSearchDate.value!!)
        } else {
            getMainActivity()?.showNetworkError()
        }
        radiogroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
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
                        searchItem?.isEnabled = true
                        view.highlightColor = resources.getColor(android.R.color.holo_orange_light)
                        //view.setTextColor(resources.getColor(android.R.color.holo_orange_light))
                    }
                R.id.local_search ->
                    if (checked) {
                        searchItem?.isEnabled = true
                        view.highlightColor = resources.getColor(android.R.color.holo_orange_light)
                        //view.setTextColor(resources.getColor(android.R.color.holo_orange_light))
                    }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.options_menu, menu)
        searchItem = menu?.findItem(R.id.search)

        //Get the search menu item
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = if (searchItem == null) null else searchItem?.actionView as SearchView

        if (searchView != null) {
            searchEditText = searchView!!.findViewById<EditText>(R.id.search_src_text) as? EditText
            searchView?.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
            searchView?.setIconifiedByDefault(false)
            if (!local_search!!.isChecked && !global_search!!.isChecked) {
                searchItem?.isEnabled = false
            }
            searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String?): Boolean {
                    if (notelist.adapter != null && local_search!!.isChecked) {
                        if (TextUtils.isEmpty(newText)) {
                            notelist.clearTextFilter()
                            (notelist.adapter as NoteAdapter).getFilter().filter("")
                        } else {
                            (notelist.adapter as NoteAdapter).getFilter().filter(newText)
                        }
                    }
                    return true
                }

                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (global_search!!.isChecked && searchEditText?.text != null) {
                        if (FamilyNoteApplication.familyNoteApplication!!.isInternetAvailable()) {
                            noteSearchModel.searchNote(searchEditText?.text.toString())
                        } else {
                            getMainActivity()?.showNetworkError()
                        }
                        hideKeyboard()
                        return true
                    }
                    return false
                }
            })

            searchView?.setOnQueryTextFocusChangeListener(object : View.OnFocusChangeListener {
                override fun onFocusChange(v: View, hasFocus: Boolean) {
                    if (!hasFocus) {
                        Log.i(javaClass.name, "SearchView lost focus")
                    } else {
                        Log.i(javaClass.name, "SearchView got focus")
                    }
                }
            })

            //For catch up user click 'X' to clear text.
            val closeButton = searchView?.findViewById<ImageView>(R.id.search_close_btn)
            closeButton?.setOnClickListener {
                searchEditText?.setText("")

                //savedSearchQuery = ""
                if (notelist.adapter != null) {
                    notelist.clearTextFilter()
                    (notelist.adapter as NoteAdapter).getFilter().filter("")
                }
            }
        }
    }

    private fun observeViewModel(viewModel : NoteSearchViewModel) {

        viewModel.noteSearchResult.observe(this,  object : Observer<BaseResult> {
            override fun onChanged(@Nullable baseResult: BaseResult?) {
                dismissProgressDialog()
                if (baseResult?.resultCode == TimeoutError) {
                    getMainActivity()?.logout()
                    getMainActivity()?.showLoginActivity(getMainActivity() as RootActivity)
                } else if (baseResult?.resultCode == 21) {
                    FamilyNoteApplication.familyNoteApplication?.putKeyValue(resources.getString(R.string.token), null)
                    noteSearchModel.searchNote(searchEditText?.text.toString())
                } else if (baseResult!!.isSuccess()){
                    val gson = Gson()

                    val type = object : TypeToken<ArrayList<Note>>() {

                    }.type
                    val jsonText = gson.toJson(baseResult.resultDesc)
                    noteList = gson.fromJson(jsonText, type)

                    noteAdapter = NoteAdapter(activity!!.applicationContext, noteList)
                    notelist.adapter = noteAdapter
                    noteAdapter?.notifyDataSetChanged()
                } else {
                    getMainActivity()?.errorHandler(baseResult.resultDesc.toString(), "Filter failed!")
                }
            }
        })
    }
}