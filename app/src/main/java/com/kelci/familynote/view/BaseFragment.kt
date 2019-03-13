package com.kelci.familynote.view

import android.support.v4.app.Fragment
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlin.reflect.KClass

open class BaseFragment : Fragment() {

    companion object {

        fun newInstance(): BaseFragment {
            return BaseFragment()
        }
    }
    val name = BaseFragment::class.java.name
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.i(name, "the onCreateView was called" )
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onResume() {
        Log.i(name, "the onResume was called" )
        super.onResume()
    }

    override fun onStop() {
        Log.i(name, "the onStop was called" )
        super.onStop()
    }

    override fun onAttach(context: Context?) {
        Log.i(name, "the onAttach was called" )
        super.onAttach(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.i(name, "the onActivityCreated was called" )
        super.onActivityCreated(savedInstanceState)
    }

    override fun onStart() {
        Log.i(name, "the onStart was called" )
        super.onStart()
    }

    override fun onDestroyView() {
        Log.i(name, "the onDestroyView was called" )
        super.onDestroyView()
    }
}