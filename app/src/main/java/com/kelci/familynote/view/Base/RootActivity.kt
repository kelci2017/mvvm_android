package com.kelci.familynote.view.Base

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

open class RootActivity : AppCompatActivity() {

    val name = RootActivity::class.java.name

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        Log.i(name, "the onCreate was called in the root activity" )
    }

    override fun onStart() {
        super.onStart()
        Log.i(name, "the onStart was called in the root activity" )
    }

    override fun onPause() {
        super.onPause()
        Log.i(name, "the onPause was called in the root activity" )
    }

    override fun onStop() {
        super.onStop()
        Log.i(name, "the onStop was called in the root activity" )
    }

    override fun onResume() {
        super.onResume()
        Log.i(name, "the onResume was called in the root activity" )
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(name, "the onRestart was called in the root activity" )
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(name, "the onDestroy was called in the root activity" )
    }
}