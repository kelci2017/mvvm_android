package com.kelci.familynote.view.Settings

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.*
import com.kelci.familynote.R
import com.kelci.familynote.Utilities.CommonUtil
import com.kelci.familynote.viewmodel.NoteSearchViewModel
import java.util.*


class SettingsAdapter(context : SettingsFragment, items : ArrayList<Item>) : BaseAdapter() {
    private var context : SettingsFragment = context
    private var items : ArrayList<Item> = items
    private var itemTitle : TextView? = null
    private var itemSubtitle : TextView? = null
    private var calendarView : CalendarView? = null
    private var itemLayout : LinearLayout? = null
    private var selectedDate : String = CommonUtil.getTodayDate()
    private var divider : View? = null
    private var senderName = "All"
    private var receiverName = "All"

    override fun getCount(): Int {
        return items.count()
    }

    override fun getItem(p0: Int): Any {
        return items[p0]
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {

        val inflater = context.getMainActivity()?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var convertview = p1
        if (items[p0].isSection()) {
            // if section header
            convertview = inflater.inflate(R.layout.settings_section, p2, false)
            val tvSectionTitle = convertview.findViewById(R.id.tvSectionTitle) as TextView
            tvSectionTitle.text = items[p0].getTitle()
        } else {
            // if item
            convertview = inflater.inflate(R.layout.settings_item, p2, false)
            itemTitle = convertview.findViewById(R.id.title) as TextView
            itemSubtitle = convertview.findViewById(R.id.subtitle) as TextView
            calendarView = convertview.findViewById(R.id.calendarView) as CalendarView

            calendarView?.setOnDateChangeListener { view, year, month, dayOfMonth ->
                when (month < 9) {
                    true -> selectedDate = "" + year + "-0" + (month + 1) + "-" + dayOfMonth
                    false -> selectedDate = "" + year + "-" + (month + 1) + "-" + dayOfMonth
                }
                context.setNoteFilter()
            }

            itemTitle?.text = items[p0].getTitle()
            itemSubtitle?.text = items[p0].getSubtitle()
            itemLayout = convertview.findViewById(R.id.item_layout) as LinearLayout
            divider = convertview.findViewById(R.id.divider) as View

            if (itemSubtitle?.text == "") {
                itemSubtitle?.visibility = View.GONE
            }

            setCalendarViewStyle(calendarView)

            if (itemTitle?.text != "") {
                calendarView?.visibility = View.GONE
            }

            if (p0 == 3) {
                itemSubtitle?.text = selectedDate
            }

            if (items.count() > 8 && p0 == 3) {
                hideDivider()
            }

            if (items.count() <= 8 && p0 == 3) {
                showDivider()
            }
//            if (p0 == 1) {
//                itemSubtitle?.text = senderName
//            }
//            if (p0 == 2) {
//                itemSubtitle?.text = receiverName
//            }

        }

        return convertview
    }

    private fun showDivider() {
        divider?.visibility = View.VISIBLE
    }

    private fun hideDivider() {
        divider?.visibility = View.GONE
    }
    private fun setCalendarViewStyle(calendarView: CalendarView?) {

        var day =1
        val calendarF = Calendar.getInstance()
        val calendarL = Calendar.getInstance()
        val calendarC = Calendar.getInstance()

        var days = calendarF.getActualMaximum(Calendar.DAY_OF_MONTH)
        var date = calendarF.get(Calendar.DAY_OF_MONTH)
        var year = calendarF.get(Calendar.YEAR)
        var month = calendarF.get(Calendar.MONTH)
        calendarF.set(year, month, day)
        calendarL.set(year, month, days)
        calendarC.set(year, month, date)

        calendarView?.minDate = calendarF.timeInMillis
        calendarView?.maxDate = calendarL.timeInMillis
        calendarView?.date = calendarC.timeInMillis
    }
    override fun getItemId(p0: Int): Long {

        return p0.toLong()
    }

    fun setSender(name : String) {
        senderName = name
    }

    fun setReceiver(name : String) {
        receiverName = name
    }

    fun getSenderName() : String{
        return senderName
    }

    fun getReveiverName() : String {
        return receiverName
    }

    fun getDate() : String {
        return selectedDate
    }
}