package com.kelci.familynote.view

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.TabLayout
import android.support.v4.view.GravityCompat
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.MenuItem
import android.view.View
import com.kelci.familynote.R
import kotlinx.android.synthetic.main.activity_main_tabs.*

class MainActivity : RootActivity {

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main_tabs)
        tabLayout = findViewById<TabLayout>(R.id.tab_layout) as TabLayout
        viewPager = findViewById<ViewPager>(R.id.pager) as ViewPager

        pagerAdapter = SettingsPagerAdapter(supportFragmentManager, tabLayout!!.tabCount)
        viewPager!!.adapter = pagerAdapter
        //tab_layout?.setupWithViewPager(pager)


        viewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        tabLayout!!.addOnTabSelectedListener(object :
                TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                Log.i("MainActivity", "The tab is selected")
                viewPager?.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }

        })
    }
    }