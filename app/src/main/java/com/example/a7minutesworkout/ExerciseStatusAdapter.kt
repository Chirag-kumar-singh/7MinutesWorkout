package com.example.a7minutesworkout

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView


//TODO(Step 10.1 : Creating an adapter class for recyclerView using the item designed for it and along with exercise model class.)
class ExerciseStatusAdapter(val items: ArrayList<ExerciseModel>, val context: Context): RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>() {

    /**
     * A viewHolder describes an item and metadata about its place within
     * the recyclerView.
     */

    class ViewHolder(view:View) : RecyclerView.ViewHolder(view){
        // Holds the TextView that will add each item to
        val tvItem = view.findViewById<TextView>(R.id.tvItem)
    }

    /**
     * Inflates the item view which is designed in xml layout file
     *
     * create a new
     * {@link ViewHolder} and initializes some private fields to be used by recyclerview.
     *
     */

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_exercise_status, parent, false))
    }

    /**
     * Gets the number of items in the list
     */

    override fun getItemCount(): Int {
        return items.size
    }

    /**
     * Binds each item in the arraylist to a view
     *
     * called when recyclerview need a new (@link ViewHolder} of the given type to represent
     * an item.
     *
     * This new ViewHolder should be constructed with a new view that
     * can represent the items of the given type. You can either create a new
     * view manually or inflate it form an XML layout file.
     *
     * It also set the looks.
     */

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model:ExerciseModel = items[position]
        holder.tvItem.text = model.getId().toString()

        //TODO(12.4 - Updating the current item and the completed item in the UI and changing the background amd text color according to it.)
        //Updating the background and text color according to the flags that is in the list.
        //A link to set text color programmatically and same way we can set the drawable background also instead of color.
        // https://stackoverflow.com/questions/8472349/how-to-set-text-color-to-a-text-view-programmatically

        if(model.getIsSelected()){
            holder.tvItem.background = ContextCompat.getDrawable(context, R.drawable.item_circular_thin_accent_border)
            holder.tvItem.setTextColor(Color.parseColor("#212121"))
        }else if(model.getIsCompleted()){
            holder.tvItem.background = ContextCompat.getDrawable(context, R.drawable.item_circular_color_accent_background)
            holder.tvItem.setTextColor(Color.parseColor("#FFFFFF"))
        }else{
            holder.tvItem.background = ContextCompat.getDrawable(context, R.drawable.item_circular_color_gray_background)
            holder.tvItem.setTextColor(Color.parseColor("#212121"))
        }
    }
}