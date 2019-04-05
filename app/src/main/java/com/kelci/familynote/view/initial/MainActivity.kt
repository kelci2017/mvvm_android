package com.kelci.familynote.view.initial

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.MenuItem
import com.kelci.familynote.R
import android.widget.TextView
import com.kelci.familynote.FamilyNoteApplication
import com.kelci.familynote.model.dataStructure.BaseResult
import com.kelci.familynote.model.restService.rest_client.ServiceUtil
import com.kelci.familynote.view.base.RootActivity
import com.kelci.familynote.view.settings.SettingsFragment
import com.kelci.familynote.view.settings.SettingsPagerAdapter
import restclient.RestHandler
import restclient.RestResult


class MainActivity : RootActivity() {

    private var tabLayout : TabLayout? = null
    private var viewPager: ViewPager? = null
    private var pagerAdapter: SettingsPagerAdapter? = null
    private var searchItem: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main_tabs)
        tabLayout = findViewById<TabLayout>(R.id.tab_layout) as TabLayout
        viewPager = findViewById<ViewPager>(R.id.pager) as ViewPager

        pagerAdapter = SettingsPagerAdapter(supportFragmentManager, tabLayout!!.tabCount)
        viewPager?.offscreenPageLimit = 2
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

            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }

        })

        FamilyNoteApplication.familyNoteApplication?.initPushNotifications()
        if (FamilyNoteApplication.familyNoteApplication?.getKeyArraylist(FamilyNoteApplication.familyNoteApplication?.resources!!.getString(R.string.member_list)) == null) {
            FamilyNoteApplication.familyNoteApplication?.getFamilyMemberList()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val fragmentManager = supportFragmentManager
        for (fragment in fragmentManager.fragments) {
            if (fragment is SettingsFragment) {
                fragment.onResume()
            }
        }
    }

    private fun getFamilyMemberList() {
        var restHandler : RestHandler<BaseResult>? = null

        restHandler as RestHandler<Any>?


       ServiceUtil.getFamilyMemberList(null,null,object : RestHandler<Any>(){
            override fun onReturn(result: RestResult<Any>?) {

                val baseResult : BaseResult? = result?.resultObject as? BaseResult

                if (baseResult != null) {
                    val familyMemberList : ArrayList<String>? = baseResult.resultDesc as ArrayList<String>
                    if (familyMemberList != null) {
                        FamilyNoteApplication.familyNoteApplication?.putKeyArralylist(resources.getString(R.string.member_list), familyMemberList)
                    }
                }
            }
        }, false)
    }
    }