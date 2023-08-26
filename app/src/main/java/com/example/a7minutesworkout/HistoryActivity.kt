package com.example.a7minutesworkout

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// TODO(Step 20.2 : Adding the History Screen Activity.)
// START

class HistoryActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)


        // TODO(Step 20.5 : Setting up the action bar in the History Screen Activity and adding a back arrow button and click event for it.)
        // START

        var toolbar_history_activity = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_history_activity)

        setSupportActionBar(toolbar_history_activity)

        val actionbar = supportActionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)   //set back button
        supportActionBar?.title = "HISTORY"   //setting a titlein the action bar.

        toolbar_history_activity.setNavigationOnClickListener{
            onBackPressed()
        }

        //TODO(22.3 - Calling a function for getting the list of completed dates when hsitory screen is launched.
        getAllCompletedDates()
    }

    //TODO(22.2 - Created a function to get the list of completed dates from the History table.)
    /**
     * Function is used to get the list exercise completed dates.
     */

    private fun getAllCompletedDates(){

        //Instance of the Sqlite Open Helper class.
        val dbHandler = SqlLiteOpenHelper(this, null)   //List of history table

        val allCompletedDatesList =
            dbHandler.getAllCompletedDatesList()    //list of history table.

        //List items are printed in log.
//        for (i in allCompletedDatesList){
//            Log.e("Date : ", "" + 1)
//        }

        //TODO(22.3 - Now here the dates which were printed in log.
        // We will pass the list to the adapter class which we have created and bind it to the recycler view.)
        if(allCompletedDatesList.size > 0){
            //Here if the List size is greater than 0 w will display the item in the recycler view or
            // else we will show the text view that no data is available.
            val tvHistory = findViewById<TextView>(R.id.tvHistory)
            val rvHistory = findViewById<RecyclerView>(R.id.rvHistory)
            val tvNodataAvailable = findViewById<TextView>(R.id.tvNoDataAvailable)

            tvHistory.visibility = View.VISIBLE
            rvHistory.visibility = View.VISIBLE
            tvNodataAvailable.visibility = View.GONE

            //Creates a vertical layout Manager
            rvHistory.layoutManager = LinearLayoutManager(this)

            // History adapter is initialized and the list is passed in the param.
            val historyAdapter = HistoryAdapter(this, allCompletedDatesList)

            // Access the RecyclerView adapter and load the data into it
            rvHistory.adapter = historyAdapter
        }else{
            val tvHistory = findViewById<TextView>(R.id.tvHistory)
            val rvHistory = findViewById<RecyclerView>(R.id.rvHistory)
            val tvNodataAvailable = findViewById<TextView>(R.id.tvNoDataAvailable)

            tvHistory.visibility = View.GONE
            rvHistory.visibility = View.GONE
            tvNodataAvailable.visibility = View.VISIBLE
        }
    }

}