package com.kelci.familynote.view

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.*
import com.kelci.familynote.R
import java.util.*


class SettingsAdapter(context : Context, items : ArrayList<Item>) : BaseAdapter() {
    private var context : Context = context
    private var items : ArrayList<Item> = items
    private var originalItems : ArrayList<Item>? = null
    private var itemTitle : TextView? = null
    private var itemSubtitle : TextView? = null
    private var calendarView : CalendarView? = null
    private var title_layout : LinearLayout? = null
    private var itemLayout : LinearLayout? = null
    private var selectedDate : String = "Today"

    override fun getCount(): Int {
        return items.count()
    }

    override fun getItem(p0: Int): Any {
        return items[p0]
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        //val convertview : View?
        var convertview = p1
        if (items[p0].isSection()) {
            // if section header
            convertview = inflater.inflate(R.layout.settings_section, p2, false)
            //p1 = inflater.inflate(R.layout.settings_section, p2, false)
            val tvSectionTitle = convertview.findViewById(R.id.tvSectionTitle) as TextView
            tvSectionTitle.text = items[p0].getTitle()
            //tvSectionTitle.setText((items.get(p0) as SettingsSection).getTitle())
        } else {
            // if item
            convertview = inflater.inflate(R.layout.settings_item, p2, false)
            //p1 = inflater.inflate(R.layout.settings_item, p2, false)
            itemTitle = convertview.findViewById(R.id.title) as TextView
            itemSubtitle = convertview.findViewById(R.id.subtitle) as TextView
            calendarView = convertview.findViewById(R.id.calendarView) as CalendarView
            itemTitle?.text = items[p0].getTitle()
            itemSubtitle?.text = items[p0].getSubtitle()
            itemLayout = convertview.findViewById(R.id.item_layout) as LinearLayout

            if (itemSubtitle?.text == "") {
                itemSubtitle?.visibility = View.GONE
            }

            setCalendarViewStyle(calendarView)

            if (itemTitle?.text != "") {
                calendarView?.visibility = View.GONE
            }

            if (p0 == 3) {
                itemSubtitle?.text = items[p0].getSubtitle()
            }
//            if (p0 != 4) {
//                calendarView?.isEnabled = false
//            }
            //selectDate()

        }

        return convertview
    }

    fun selectDate(calendarView: CalendarView?) {
        calendarView?.setOnDateChangeListener { view, year, month, dayOfMonth ->
            // Note that months are indexed from 0. So, 0 means January, 1 means february, 2 means march etc.
            selectedDate = "" + year + "-" + (month + 1) + "-" + dayOfMonth
        }
    }
    private fun setCalendarViewStyle(calendarView: CalendarView?) {
        var year = Calendar.YEAR
        var month = Calendar.MONTH
        //var date = Calendar.DAY_OF_MONTH
        //Log.i("SettingsAdapter", "the current day of month is: " + date.toString())
        var day =1


        val calendarF = Calendar.getInstance()
        val calendarL = Calendar.getInstance()
        val calendarC = Calendar.getInstance()

        var days = calendarF.getActualMaximum(Calendar.DAY_OF_MONTH)
        var date = calendarF.get(Calendar.DAY_OF_MONTH)
        calendarF.set(year, month, day) //want to show full month of February 2010
        calendarL.set(year, month, days)
        calendarC.set(year, month, date)

        calendarView?.minDate = calendarF.timeInMillis
        calendarView?.maxDate = calendarL.timeInMillis
        calendarView?.date = calendarC.timeInMillis
    }
    override fun getItemId(p0: Int): Long {

        return p0.toLong()
    }
}