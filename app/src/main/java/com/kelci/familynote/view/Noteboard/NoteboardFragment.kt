package com.kelci.familynote.view.Noteboard

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
import com.kelci.familynote.view.Base.BaseFragment
import com.kelci.familynote.view.Base.RootActivity
import com.kelci.familynote.viewmodel.NoteSearchViewModel

class NoteboardFragment : BaseFragment() {

    private var radioGroup : RadioGroup? = null
    private var rootView : View? = null
    private var noteList = ArrayList<Note>()
    private var noteAdapter : NoteAdapter? = null
    private var noteListView : ListView? = null
    private var localSearchRadioButton : RadioButton? = null
    private var globalSearchRadioButton : RadioButton? = null
    private var searchItem: MenuItem? = null
    private var searchView: SearchView? = null
    private var searchEditText: EditText? = null
    private lateinit var noteSearchModel: NoteSearchViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        rootView = inflater.inflate(R.layout.fragment_noteboard, container, false)
        radioGroup = rootView?.findViewById<RadioGroup>(R.id.radiogroup)
        noteListView = rootView?.findViewById(R.id.notelist)
        localSearchRadioButton = rootView?.findViewById(R.id.local_search)
        globalSearchRadioButton = rootView?.findViewById(R.id.global_search)

        setHasOptionsMenu(true)

        noteSearchModel = ViewModelProviders.of(getMainActivity() as FragmentActivity).get(NoteSearchViewModel::class.java)
        observeViewModel(noteSearchModel)

        //noteSearchModel.filterNote(noteSearchModel.noteSearchSender.value!!, noteSearchModel.noteSearchReceiver.value!!, noteSearchModel.noteSearchDate.value!!)

        return rootView
    }

    override fun onResume() {
        super.onResume()
        noteSearchModel.filterNote(noteSearchModel.noteSearchSender.value!!, noteSearchModel.noteSearchReceiver.value!!, noteSearchModel.noteSearchDate.value!!)
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
            if (!localSearchRadioButton!!.isChecked && !globalSearchRadioButton!!.isChecked) {
                searchItem?.isEnabled = false
            }
            searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String?): Boolean {
                    if (noteListView?.adapter != null && localSearchRadioButton!!.isChecked) {
                        if (TextUtils.isEmpty(newText)) {
                            noteListView?.clearTextFilter()
                            (noteListView?.adapter as NoteAdapter).getFilter().filter("")
                        } else {
                            (noteListView?.adapter as NoteAdapter).getFilter().filter(newText)
                        }
                    }
                    return true
                }

                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (globalSearchRadioButton!!.isChecked && searchEditText?.text != null) {
                        noteSearchModel.searchNote(searchEditText?.text.toString())
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
                if (noteListView?.adapter != null) {
                    noteListView?.clearTextFilter()
                    (noteListView?.adapter as NoteAdapter).getFilter().filter("")
                }
            }
        }
    }

    private fun observeViewModel(viewModel : NoteSearchViewModel) {

        viewModel.noteSearchResult.observe(this,  object : Observer<BaseResult> {
            override fun onChanged(@Nullable baseResult: BaseResult?) {
                if (baseResult?.getResultCode() == 21) {
                    FamilyNoteApplication.familyNoteApplication?.putKeyValue(resources.getString(R.string.token), null)
                    noteSearchModel.searchNote(searchEditText?.text.toString())
                    return
                }
                dismissProgressDialog()
                if (baseResult?.getResultCode() == TimeoutError) {
                    getMainActivity()?.showLoginActivity(getMainActivity() as RootActivity)
                }
                if (!baseResult!!.isSuccess()) {
                    getMainActivity()?.errorHandler(baseResult.getResultDesc().toString(), "Filter failed!")
                    return
                }
                val gson = Gson()

                val type = object : TypeToken<ArrayList<Note>>() {

                }.type
                val jsonText = gson.toJson(baseResult.getResultDesc())
                noteList = gson.fromJson(jsonText, type)

                noteAdapter = NoteAdapter(activity!!.applicationContext, noteList)
                noteListView?.adapter = noteAdapter
                noteAdapter?.notifyDataSetChanged()
            }
        })
    }
}