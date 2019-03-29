package com.kelci.familynote.view.noteboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.kelci.familynote.R
import com.kelci.familynote.model.dataStructure.Note
import java.util.*

class NoteAdapter(context : Context, items : ArrayList<Note>) : BaseAdapter() {

    private var context : Context = context
    private var items : ArrayList<Note> = items
    private var originalItems : ArrayList<Note>? = null

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
        convertview = inflater.inflate(R.layout.note_list_item, p2, false)

        val sender = convertview.findViewById(R.id.sender) as TextView
        val receiver = convertview.findViewById(R.id.receiver) as TextView
        val date = convertview.findViewById(R.id.date) as TextView
        val noteBody = convertview.findViewById(R.id.notebody) as TextView
        val noteIcon = convertview.findViewById(R.id.note_icon) as ImageView

        sender.text = items[p0].getFromWhom()
        receiver.text = items[p0].getToWhom()
        date.text = items[p0].getCreated().substring(0,10)
        noteBody.text = items[p0].getNotebody()
        noteIcon.setBackgroundResource(R.drawable.ic_noteimage)

        return convertview
    }

    override fun getItemId(p0: Int): Long {

        return p0.toLong()
    }

    fun getFilter() : Filter {
        var filter : Filter?
        filter = object : Filter() {
            override fun performFiltering(constraint: CharSequence): Filter.FilterResults? {

                val results = FilterResults()
                val filteredArrayList = ArrayList<Note>()
                var constraints = constraint


                if (originalItems == null || originalItems?.count() === 0) {
                    originalItems = ArrayList<Note>(items)
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
                        val noteBody = originalItems!![i].getNotebody().toLowerCase(Locale.ENGLISH)
                        if (noteBody.contains(constraints.toString())) {
                            filteredArrayList.add(originalItems!![i])
                        }
                    }
                    results.count = filteredArrayList.count()
                    results.values = filteredArrayList
                }

                return results
            }

            override fun publishResults(constraint: CharSequence, results: Filter.FilterResults) {
                items = results.values as ArrayList<Note>
                notifyDataSetChanged()
            }
        }
        return filter
    }
}