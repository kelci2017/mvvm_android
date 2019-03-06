package com.kelci.familynote.view

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

open class RootActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        Log.i("RootActivity", "the onCreate was called in the root activity" )
    }

    override fun onStart() {
        super.onStart()
        Log.i("RootActivity", "the onStart was called in the root activity" )
    }

    override fun onPause() {
        super.onPause()
        Log.i("RootActivity", "the onPause was called in the root activity" )
    }

    override fun onStop() {
        super.onStop()
        Log.i("RootActivity", "the onStop was called in the root activity" )
    }

    override fun onResume() {
        super.onResume()
        Log.i("RootActivity", "the onResume was called in the root activity" )
    }

    override fun onRestart() {
        super.onRestart()
        Log.i("RootActivity", "the onRestart was called in the root activity" )
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("RootActivity", "the onDestroy was called in the root activity" )
    }
}