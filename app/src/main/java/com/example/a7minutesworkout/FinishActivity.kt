package com.example.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import java.text.SimpleDateFormat
import java.util.*

@Suppress("DEPRECATION")
class FinishActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {



        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish)

        var toolbar_finish_activity : Toolbar = findViewById(R.id.toolbar_finish_activity)
        var btnFinish = findViewById<Button>(R.id.btnFinish)

        //TODO(13.2 - Setting the action Bar with the toolbar added in the activity layout and adding a back arrow button and its click event.)

        setSupportActionBar(toolbar_finish_activity)
        val actionbar = supportActionBar    //Actionbar

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar_finish_activity.setNavigationOnClickListener{
            onBackPressed()
        }

        //TODO(13.5 - Adding a click event to the Finish button.)

        btnFinish.setOnClickListener{
            finish()
        }

        //TODO(21.4 - Calling the function to insert the date into the database.)
        addDatetoDatbase()
    }

    //Step 21.2 - Creating the database and inserting the date of Completion of the Exercise.)


    /**
     * Function is used to insert the current system data in the sqlite database.
     */

    private fun addDatetoDatbase(){

        val c = Calendar.getInstance()  // Calendars Current Instance
        val dateTime = c.time   //Current date and time of the system.
        Log.e("Date: ", "" + dateTime) //printed in the log.

        /**
         * Here we have taken an instance of data formatter as it will format our
         * selected date in the format which we pass it as an parameter and Locale.
         * Here I have passed the format as dd MM yyyy HH:mm:ss.
         *
         * The Locale : Gets the current value of the default lacale for this instance
         * of the Java Virtual Machine.
         */

        val sdf = SimpleDateFormat("dd MMM yyy HH:mm:ss", Locale.getDefault())  //Date formatter
        val date = sdf.format(dateTime) // dateTime is formatted in the given format.
        Log.e("Formatted Date : ", "" + date)   //Formatted date is printed in the log.

        //Instance of the SQLite Open Helper class.
        val dbHandler = SqlLiteOpenHelper(this, null)
        dbHandler.addDate(date) //Add date function is called
        Log.e("Date: ", "Added...") //Printed in log which is printed if the complete execution is done.
    }
}