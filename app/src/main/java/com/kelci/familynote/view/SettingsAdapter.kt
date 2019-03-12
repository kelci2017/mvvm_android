package com.kelci.familynote.view

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.TextView
import com.kelci.familynote.R.id.tvSectionTitle
import android.view.LayoutInflater
import android.widget.CalendarView
import com.kelci.familynote.R
import com.kelci.familynote.R.id.parent
import java.time.YearMonth
import java.util.*


class SettingsAdapter(context : Context, items : ArrayList<Item>) : BaseAdapter() {
    private var context : Context = context
    private var items : ArrayList<Item> = items
    private var originalItems : ArrayList<Item>? = null
    private var itemTitle : TextView? = null
    private var itemSubtitle : TextView? = null
    private var calendarView : CalendarView? = null
    private var calendarViewDate : CalendarView? = null

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

            if (itemSubtitle?.text == "") {
                itemSubtitle?.visibility = View.GONE
            }

            if (p0 == 3) {
                calendarViewDate = calendarView
            }
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

            val vg = calendarView?.getChildAt(0) as ViewGroup

            val subView = vg.getChildAt(1)

            if (subView is TextView) {
                Log.i("SettingsAdapter", "the first subview is textview")
                subView.visibility = View.GONE
            }


            if (itemTitle?.text != convertview.resources.getString(R.string.settings_date)) {
                calendarView?.visibility = View.GONE
            }
        }

        //p1 = convertview

        return convertview
    }

    override fun getItemId(p0: Int): Long {

        return p0.toLong()
    }

    fun expandClapseDate() {
        Log.i("settingsadapter", "the calendarview visibility is: " + (calendarView?.visibility == View.GONE).toString())
        if (calendarViewDate?.visibility == View.GONE) {
            calendarViewDate?.visibility = View.VISIBLE
        } else {
            calendarViewDate?.visibility = View.GONE
        }
        //notifyDataSetChanged()
    }
    fun getFilter() : Filter {
        var filter : Filter?
        filter = object : Filter() {
            override fun performFiltering(constraint: CharSequence): Filter.FilterResults? {

                val results = FilterResults()
                val filteredArrayList = ArrayList<Item>()
                var constraints = constraint


                if (originalItems == null || originalItems?.count() === 0) {
                    originalItems = ArrayList<Item>(items)
                }

                /*
                     * if constraint is null then return original value
                     * else return filtered value
                     */
                if (constraints == null && constraints.length === 0) {
                    results.count = originalItems!!.count()
                    results.values = originalItems
                } else {
                    constraints = constraints.toString().toLowerCase(Locale.ENGLISH)
                    for (i in 0 until originalItems!!.count()) {
                        val title = originalItems!![i].getTitle().toLowerCase(Locale.ENGLISH)
                        if (title.startsWith(constraints.toString())) {
                            filteredArrayList.add(originalItems!![i])
                        }
                    }
                    results.count = filteredArrayList.count()
                    results.values = filteredArrayList
                }

                return results
            }

            override fun publishResults(constraint: CharSequence, results: Filter.FilterResults) {
                items = results.values as ArrayList<Item>
                notifyDataSetChanged()
            }
        }
        return filter
    }
}