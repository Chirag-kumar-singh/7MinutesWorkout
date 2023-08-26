package com.example.a7minutesworkout

import android.content.Context
import android.graphics.Color
import android.icu.text.Transliterator.Position
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HistoryAdapter(val context: Context, val items: ArrayList<String>): RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {


    /**
     * A viewHolder describes an item view and metadata about its place within the RecyclerView.
     */
    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        //Holds the textView that will add each item to
        val llHistoryItemMain = view.findViewById<LinearLayout>(R.id.ll_history_item_main)!!
        val tvItem = view.findViewById<TextView>(R.id.tvItem)
        val tvPosition = view.findViewById<TextView>(R.id.tvPosition)
    }

    /**
     * Inflates the item view which is designed in xml layout file
     * create a new
     * {@link ViewHolder} and initializes some private fields to be used by RecyclerView.
     */

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.items_history_row,
                parent,
                false
            )
        )
    }

    /**
     * Binds each item in the ArrayList to a view.
     *
     * Called when RecyclerView need a new {@link viewHolder} of the given type to represent
     * an item.
     *
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate ot from an XML Layout file.
     */

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val date: String = items.get(position)

        holder.tvPosition.text = (position + 1).toString()
        holder.tvItem.text = date

        //updating the background color according to the even/odd position int list.
        if(position % 2 == 0){
            holder.llHistoryItemMain.setBackgroundColor(
                Color.parseColor("#EBEBEB")
            )
        } else {
            holder.llHistoryItemMain.setBackgroundColor(
                Color.parseColor("#FFFFFF")
            )
        }

    }

    /**
     * Gets the number of items in the list
     */

    override fun getItemCount(): Int {
        return items.size
    }


}