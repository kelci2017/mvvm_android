package com.kelci.familynote.view

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.support.test.espresso.web.model.Atoms.getTitle
import android.widget.TextView
import com.kelci.familynote.R.id.tvSectionTitle
import android.view.LayoutInflater
import com.kelci.familynote.R
import com.kelci.familynote.R.id.parent


class SettingsAdapter(context : Context, items : ArrayList<Item>) : BaseAdapter() {
    private var context : Context = context
    private var items : ArrayList<Item> = items

    override fun getCount(): Int {
        return items.count()
    }

    override fun getItem(p0: Int): Any {
        return items[p0]
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        if (items.get(p0).isSection()) {
            // if section header
            p1 = inflater.inflate(R.layout.settings_section, p2, false)
            val tvSectionTitle = p1.findViewById(R.id.tvSectionTitle) as TextView
            tvSectionTitle.text = items[p0].getTitle()
            //tvSectionTitle.setText((items.get(p0) as SettingsSection).getTitle())
        } else {
            // if item
            p1 = inflater.inflate(R.layout.settings_item, p2, false)
            val tvItemTitle = p1.findViewById(R.id.title) as TextView
            val tvItemSubTitle = p1.findViewById(R.id.title) as TextView
            tvItemTitle.text = items[p0].getTitle()
            tvItemSubTitle.text = items[p0].getSubtitle()
        }

        return p1
    }

    override fun getItemId(p0: Int): Long {
        return p0 as Long
    }

    fun getFilter() : Filter {
        var filter : Filter?
        filter = object : Filter() {
            override fun performFiltering(constraint: CharSequence): Filter.FilterResults? {

                return null
            }

            override fun publishResults(constraint: CharSequence, results: Filter.FilterResults) {
                items = results.values as ArrayList<Item>
                notifyDataSetChanged()
            }
        }
        return filter
    }
}