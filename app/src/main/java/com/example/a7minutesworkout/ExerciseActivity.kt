package com.example.a7minutesworkout

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.os.PersistableBundle
import android.provider.MediaStore.Audio.Media
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text
import java.util.Locale

//TODO(Step 2.1 - Add an ExerciseActivity,)-->
//TODO(7.1 - Add the implemented for the Text to speech feature.)
class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    //TODO(3.1 - Adding a variables for the 10 second REST timer.)
    private var restTimer: CountDownTimer? = null
    //Variable for Rest Timer and later on we will initialize it.
    private var restProgress = 0
    //Variable for timer progress ,it will count up like normal clock

    // TODO(Step 4.2 - Adding a variables for the 30 seconds Exercise timer.)
    // START
    private var exerciseTimer: CountDownTimer? = null

    private var exerciseProgress = 0

    //TODO(Step 5.4 - The variable for the exercise list and current position here it is -1 as the list starting element is 0.)
    private var exerciseList: ArrayList<ExerciseModel>? = null // We will initialize the list later.
    private var currentExercisePosition = -1    // Current Position of Exercise.

    //TODO(7.3 - variable for text to speech which will be initialized later on.)
    private var tts: TextToSpeech? = null

    private lateinit var toolbar_exercise_activity: Toolbar

    //TODO(8.1 - Declaring the variable of the media player for playing a notification sound when the exercise os about to start.)
    private var player: MediaPlayer? = null


    //TODO(11.1 - Declaring a variable of an adapter class to bind it to recycler view.)
    private var exerciseAdapter: ExerciseStatusAdapter? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        //TODO(Step 5 - Setting up the action bar using the toolbar and adding a back arrow button to it.)-->
        //START
        toolbar_exercise_activity = findViewById(R.id.toolbar_exercise_activity)
        setSupportActionBar(toolbar_exercise_activity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Us the backpressed (close the activity) functionality when clicking the toolbar back button
        //Navigate the activity on click on back button of action bar.
        toolbar_exercise_activity.setNavigationOnClickListener {
            //onBackPressed()
            //TODO(14.3 = Calling the function of custom dialog for back button confirmation which we have created in step 2.)
            customDialogForBackButton()
        }
        //END

        //TODO(5.5 - Initializing and Assigning a default exercise list to our list variable.)
        exerciseList = Constants.defaultExerciseList()

        //TODO(7.4 - Initializing the variable of text to speech).
        tts = TextToSpeech(this, this)

        //TODO(3.4 - Calling the function to make it visible on screen)-->
        setupRestView()


        //TODO(11.3 - Calling the function where we have bound the adapter to recycler view to show the data in the UI.)
        setupExerciseStatusRecyclerView()
    }

    //TODO(3.3 - Setting up the get ready view with 10 seconds of timer.)-->
    private fun setupRestView(){


        //TODO(8.2 - Playing a notification sound when the exercise is about to start when you are in the rest state
        // the sound file is added in the raw folder as resource.

        /**
         * Here sound file is added in to "raw" folder in resources.
         * and played using MediaPlayer. MediaPlayer class can be used to control playbacks
         * of audio/video files and streams.
         */

        try {
            val soundURI =
                Uri.parse("android.resource://com.example.a7minutesworkout/" + R.raw.press_start)
            player = MediaPlayer.create(applicationContext, soundURI)
            player!!.isLooping = false  //Sets the player to be looping or non-looping.
            player!!.start()
        }
        catch (e:java.lang.Exception){
            e.printStackTrace()
        }

        var llRestView = findViewById<LinearLayout>(R.id.llRestView)
        var llExerciseView = findViewById<LinearLayout>(R.id.llExerciseView)
        var tvUpComingExerciseName = findViewById<TextView>(R.id.tvUpcomingExerciseName)

        llRestView.visibility = View.VISIBLE
        llExerciseView.visibility = View.GONE
        /**
         * Here firstly we will check if the timer is running and it is not null
         * then cancel the running timer and start the new one.
         * And set the progress bar to initial which is 0
         */
        if(restTimer != null){
            restTimer!!.cancel()
            restProgress = 0
        }

        // TODO (Step 6.2 - Setting the upcoming exercise name in the UI element.)
        // START
        // Here we have set the upcoming exercise name to the text view
        // Here as the current position is -1 by default so to selected from the list it should be 0 so we have increased it by +1.

        tvUpComingExerciseName.text = exerciseList!![currentExercisePosition + 1].getName()



        setRestProgressBar()

    }

    //TODO(3.2 - Setting up the 10 seconds timer for the rest view and updating it continously.)-->
    /**
     * Function is used to set the progress of timer using the progress
     */

    private fun setRestProgressBar(){

        var progressBar = findViewById<ProgressBar>(R.id.progressBar)
        var tvTimer = findViewById<TextView>(R.id.tvTimer)


        progressBar.progress = restProgress // Sets the current progress to the specified value.

        /**
         * @param millisinFuture the number of millis in future from the call
         * to {#start()} until the countdown is done and {#onFinish()} is called.
         *
         * @param countDownInterval The interval along the way to receive
         * {#onTick(long)} callbacks.
         */


        // Here we have started a timer of 10 seconds so the 10000 is milliseconds is 10 seconds and the countdown interval is 1 second so it 1000.

        restTimer = object: CountDownTimer(2000, 1000){
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                progressBar.progress = 2 - restProgress    //indicates progressbar progress
                tvTimer.text = (2 - restProgress).toString()   // Current progress is set to text view in terms of seconds.
            }

            override fun onFinish() {
//                //When the 10 second will complete this will be executed .
//                Toast.makeText(
//                    this@ExerciseActivity,
//                    "here now we will start the exercise",
//                    Toast.LENGTH_SHORT
//                ).show()

                //TODO(5.6 - Increasing the current position of the exercise after rest view.)
                currentExercisePosition++

                //TODO(12.1 - When we are getting an updated position of exercise set that item in the list as selected and notify an adapter class .)
                exerciseList!![currentExercisePosition].setIsSelected((true))   //Current Item is selected
                exerciseAdapter!!.notifyDataSetChanged()    //Notified the current item to adapter class to reflect it into UI.


                setupExerciseView()


            }
        }.start()
    }

    //TODO(Step 3.5 - Destroying the timer when closing the activity or app.)-->
    //START
    /**
     * Here in the Destroy function we will reset the rest timer if it is running.
     */

    override fun onDestroy() {


        if(restTimer != null){
            restTimer!!.cancel()
            restProgress = 0
        }

        if(exerciseTimer != null){
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }

        // TODO (Step 7.8 - Shutting down the Text to Speech feature when activity is destroyed.)
        // START

        if(tts!= null){
            tts!!.stop()
            tts!!.shutdown()
        }

        //TODO(Step 8.3 - When the activity is destroyed if the media player instance in not null then stop it.)
        if(player != null){
            player!!.stop()
        }

        super.onDestroy()


    }

    // TODO(Step 4.4 - Setting up the Exercise View with a 30 seconds timer.)
    // START
    /**
     * Function is used to set the progress of the timer using the progress for Exercise View.
     */

    private fun setupExerciseView(){

        var llRestView = findViewById<LinearLayout>(R.id.llRestView)
        var llExerciseView = findViewById<LinearLayout>(R.id.llExerciseView)

        llRestView.visibility = View.GONE
        llExerciseView.visibility = View.VISIBLE

        if( exerciseTimer != null){
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }



        // TODO(5.7 - Setting up the current exercise name and image to view to the UI element.)
        var ivImage = findViewById<ImageView>(R.id.ivImage)
        var tvExerciseName = findViewById<TextView>(R.id.tvExerciseName)

        ivImage.setImageResource(exerciseList!![currentExercisePosition].getImage())
        tvExerciseName.text = exerciseList!![currentExercisePosition].getName()

        // TODO (Step 7.7 - Get the current exercise name from the list and pass it to the speak out method which we have created.)
        // START

        speakOut(exerciseList!![currentExercisePosition].getName())


        setExerciseProgressBar()

    }

    // TODO(Step 4.3 - After REST View Setting up the 30 seconds timer for the Exercise view and updating it continuously.)
    // START
    /**
     * Function is used to set the progress of the timer using the progress for Exercise View for 30 Seconds
     */

    private fun setExerciseProgressBar(){

        var progressBarExercise = findViewById<ProgressBar>(R.id.progressBarExercise)
        var tvExerciseTimer = findViewById<TextView>(R.id.tvExerciseTimer)


        progressBarExercise.progress = exerciseProgress // Sets the current progress to the specified value.

        /**
         * @param millisinFuture the number of millis in future from the call
         * to {#start()} until the countdown is done and {#onFinish()} is called.
         *
         * @param countDownInterval The interval along the way to receive
         * {#onTick(long)} callbacks.
         */


        // Here we have started a timer of 10 seconds so the 10000 is milliseconds is 10 seconds and the countdown interval is 1 second so it 1000.

        exerciseTimer = object: CountDownTimer(5000, 1000){
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                progressBarExercise.progress = 5 - exerciseProgress    //indicates progressbar progress
                tvExerciseTimer.text = (5 - exerciseProgress).toString()   // Current progress is set to text view in terms of seconds.
            }

            override fun onFinish() {

                //TODO(12.2 - We have changed the status of the selected item and updated the status of that, so that the position is set as completed in the exercise list.)
                if(currentExercisePosition < exerciseList?.size!! - 1) {
                    exerciseList!![currentExercisePosition].setIsSelected(false)    // exercise is completed so selection is set to false.
                    exerciseList!![currentExercisePosition].setIsCompleted(true)    // updating in the list that this exercise is completed.
                    exerciseAdapter!!.notifyDataSetChanged()    //Notifying tha adapter class.

                    //TODO(5.8 - Updating the view after completing the 10 seconds exercise.)

                    // if(currentExercisePosition < 11)
                    setupRestView()
                }

                else {
//                    //When the 10 second will complete this will be executed .
//                    Toast.makeText(
//                        this@ExerciseActivity,
//                        "Congratulations! You have completed the 7 Minutes workout.",
//                        Toast.LENGTH_SHORT
//                    ).show()

                    finish()
                    val intent = Intent(this@ExerciseActivity, FinishActivity::class.java)
                    startActivity(intent)
                }
            }
        }.start()
    }

    // TODO (Step 7.2 - Implement and override the default method of Text to Speech.)
    // START
    /**
     * This the TextToSpeech override function
     *
     * Called to signal the completion of the TextToSpeech engine initialization.
     */

    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS){
            val result = tts!!.setLanguage(Locale.UK)
            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS", "The language not supported!")
            }
            }else {
                Log.e("TTS", "Initialization failed!")
        }
    }

    private fun speakOut(text: String){
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    //TODO(11.2 - Binding adapter class to recycler view and setting the recycler view layout manager and passing a list to the adapter.)


    private fun setupExerciseStatusRecyclerView(){
        var rvExerciseStatus = findViewById<RecyclerView>(R.id.rvExerciseStatus)

        //Defining a layout manager for the recycler view
        //Here we have used a LinearLayout Manager with horizontal scroll.
        rvExerciseStatus.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL, false)


        //As the adapter expects the exercise List and context so initialize it passing it.
        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!, this)

        //Adapter class is attached to recycler view.
        rvExerciseStatus.adapter = exerciseAdapter

    }

    /**
     * Function is used to launch the custom configuration dialog.
     */

    //TODO(14.2 - Performing the stem to show the custom dialog for back button confirmation while the exercise is going on.)


    private fun customDialogForBackButton(){
        val customDialog =  Dialog(this)
        /*
        Set the screen content from a layout resource.
        The resource will be inflated, adding all top-level views to the screen.
         */

        customDialog.setContentView(R.layout.dialog_custom_back_confirmation)

        customDialog.findViewById<Button>(R.id.tvYes).setOnClickListener{
            finish()
            customDialog.dismiss()  //Dialog will be dismissed
        }
        customDialog.findViewById<Button>(R.id.tvNo).setOnClickListener{
            customDialog.dismiss()
        }
        //Start the dialog and display it on screen.
        customDialog.show()
    }


}