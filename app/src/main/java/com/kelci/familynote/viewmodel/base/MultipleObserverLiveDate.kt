package com.kelci.familynote.viewmodel.base

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.support.annotation.MainThread
import android.support.design.R.id.add
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicBoolean

class MultipleObserverLiveDate<T> : MutableLiveData<T>() {

    private val pending = AtomicBoolean(false)
//    private val observers = mutableSetOf<Observer<T>>()

    private val observers = ConcurrentHashMap<LifecycleOwner, MutableSet<ObserverWrapper<T>>>()

//    private val internalObserver = Observer<T> { t ->
//        if (pending.compareAndSet(true, false)) {
//            observers.forEach { observer ->
//                observer.onChanged(t)
//            }
//        }
//    }

//    @MainThread
//    override fun observe(owner: LifecycleOwner, observer: Observer<T>) {
//        observers.add(observer)
//
//        if (!hasObservers()) {
//            super.observe(owner, internalObserver)
//        }
//    }

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<T>) {
        val wrapper = ObserverWrapper(observer)
        val set = observers[owner]
        set?.apply {
            add(wrapper)
        } ?: run {
            val newSet = Collections.newSetFromMap(ConcurrentHashMap<ObserverWrapper<T>, Boolean>())
            newSet.add(wrapper)
            observers[owner] = newSet
        }
        super.observe(owner, wrapper)
    }

    override fun removeObservers(owner: LifecycleOwner) {
        observers.remove(owner)
        super.removeObservers(owner)
    }

    override fun removeObserver(observer: Observer<T>) {
        observers.forEach {
            if (it.value.remove(observer)) {
                if (it.value.isEmpty()) {
                    observers.remove(it.key)
                }
                return@forEach
            }
        }
        super.removeObserver(observer)
    }

    @MainThread
    override fun setValue(t: T?) {
        observers.forEach { it.value.forEach { wrapper -> wrapper.newValue() } }
        super.setValue(t)
    }

    /**
     * Used for cases where T is Void, to make calls cleaner.
     */
    @MainThread
    fun call() {
        value = null
    }

//    override fun removeObservers(owner: LifecycleOwner) {
//        observers.clear()
//        super.removeObservers(owner)
//    }
//
//    override fun removeObserver(observer: Observer<T>) {
//        observers.remove(observer)
//        super.removeObserver(observer)
//    }
//
//    @MainThread
//    override fun setValue(t: T?) {
//        pending.set(true)
//        super.setValue(t)
//    }
//
//    @MainThread
//    fun call() {
//        value = null
//    }

    private class ObserverWrapper<T>(private val observer: Observer<T>) : Observer<T> {

        private val pending = AtomicBoolean(false)

        override fun onChanged(t: T?) {
            if (pending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        }

        fun newValue() {
            pending.set(true)
        }
    }
}