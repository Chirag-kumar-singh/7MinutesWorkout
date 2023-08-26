package com.example.a7minutesworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import java.util.*

class MainActivity : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //This calls the parent constructor.
        setContentView(R.layout.activity_main)
        // This is used to align the xml view to this class

        //TODO(Step 1.6 - Adding a click event to the start button that we have created.)
        //START
        // Click event for start Button which we have created in XML.

        var llStart = findViewById<LinearLayout>(R.id.llStart);

        llStart.setOnClickListener{
            //TODO(2.11 - On Start button Click navigate it to the Exercise Activity.
            val intent = Intent(this, ExerciseActivity::class.java)
            startActivity(intent)

        }
        //TODO(Step 15.6 : Adding a click event to the BMI calculator button and navigating it to the BMI calculator feature.)
        //START
        var llBMI = findViewById<LinearLayout>(R.id.llBMI)
        llBMI.setOnClickListener{
            val intent = Intent(this, BMIActivity::class.java)
            startActivity(intent)
        }

        // TODO(Step 6 : Adding a click event to launch the History Screen Activity from Main Activity.)
        // START
         var llHistory = findViewById<LinearLayout>(R.id.llHistory)
        llHistory.setOnClickListener{
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }


    }


}