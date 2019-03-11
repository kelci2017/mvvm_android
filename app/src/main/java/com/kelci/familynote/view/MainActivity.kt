package com.kelci.familynote.view

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.SearchView
import com.kelci.familynote.R
import android.widget.TextView



class MainActivity : RootActivity, SearchView.OnQueryTextListener {

    constructor() {

    }

//    private lateinit var drawerLayout: DrawerLayout
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        drawerLayout = findViewById(R.id.drawer_layout)
//
//        val toolbar: Toolbar = findViewById(R.id.toolbar)
//        setSupportActionBar(toolbar)
//        toolbar.setBackgroundColor(resources.getColor(android.R.color.holo_orange_light))
//        val actionbar: ActionBar? = supportActionBar
//        actionbar?.apply {
//            setDisplayHomeAsUpEnabled(true)
//            setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp)
//        }
//
//        val navigationView: NavigationView = findViewById(R.id.nav_view)
//        navigationView.itemIconTintList = null
//        navigationView.setNavigationItemSelectedListener { menuItem ->
//            // set item as selected to persist highlight
//            menuItem.isChecked = true
//            // close drawer when item is tapped
//            drawerLayout.closeDrawers()
//
//            // Add code here to update the UI based on the item selected
//            // For example, swap UI fragments here
//
//            true
//        }
//
//        drawerLayout.addDrawerListener(
//                object : DrawerLayout.DrawerListener {
//                    override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
//                        // Respond when the drawer's position changes
//                    }
//
//                    override fun onDrawerOpened(drawerView: View) {
//                        // Respond when the drawer is opened
//                    }
//
//                    override fun onDrawerClosed(drawerView: View) {
//                        // Respond when the drawer is closed
//                    }
//
//                    override fun onDrawerStateChanged(newState: Int) {
//                        // Respond when the drawer motion state changes
//                    }
//                }
//        )
//    }
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            android.R.id.home -> {
//                drawerLayout.openDrawer(GravityCompat.START)
//                true
//            }
//            else -> super.onOptionsItemSelected(item)
//        }
//    }
    private var tabLayout : TabLayout? = null
    private var viewPager: ViewPager? = null
    private var pagerAdapter: SettingsPagerAdapter? = null
    private var searchItem: MenuItem? = null
    private var searchView: SearchView? = null
    private var searchEditText: EditText? = null
    private var savedFilterStr: String? = ""
    private var backButton: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main_tabs)
        tabLayout = findViewById<TabLayout>(R.id.tab_layout) as TabLayout
        viewPager = findViewById<ViewPager>(R.id.pager) as ViewPager

        pagerAdapter = SettingsPagerAdapter(supportFragmentManager, tabLayout!!.tabCount)
        viewPager!!.adapter = pagerAdapter
        //tab_layout?.setupWithViewPager(pager)
        setSupportActionBar(findViewById(R.id.my_toolbar))
        val toolbar = findViewById<Toolbar>(R.id.my_toolbar)

        val mTitle = findViewById<TextView>(R.id.toolbar_title) as TextView

        mTitle.text = toolbar.title
        supportActionBar?.setDisplayShowTitleEnabled(false);

        viewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        tabLayout!!.addOnTabSelectedListener(object :
                TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                Log.i("MainActivity", "The tab is selected")
                viewPager?.currentItem = tab.position
                searchItem?.isVisible = (tab.position == 0)
                if (tab.position != 0) {
                    toolbar.collapseActionView()
                }

            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }

        })
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        searchItem = menu.findItem(R.id.search)

        //Get the search menu item
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = if (searchItem == null) null else searchItem?.actionView as SearchView

        if (searchView != null) {
            searchEditText = searchView!!.findViewById<EditText>(R.id.search_src_text) as? EditText
            searchView?.setSearchableInfo(searchManager.getSearchableInfo(componentName))
            searchView?.setIconifiedByDefault(false)
            searchView?.setOnQueryTextListener(this)

            //restore search text
//            if (savedFilterStr != null && savedFilterStr?.length > 0) {
//                searchItem?.expandActionView()
//                searchEditText?.text = savedFilterStr
//                clearSearchViewFocus()
//            }

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
            val closeButton = searchView?.findViewById<ImageView>(R.id.search_close_btn) as? ImageView
            backButton = searchView?.findViewById<ImageView>(R.id.search_mag_icon) as? ImageView
            closeButton?.setOnClickListener { searchEditText?.setText("") }
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    fun getFilterStr(): String {
        return searchEditText?.getText().toString()
    }

    fun clearSearchViewFocus() {
        try {
            if (searchView != null) searchView?.clearFocus()
        } catch (e: Exception) {
        }

    }
    }