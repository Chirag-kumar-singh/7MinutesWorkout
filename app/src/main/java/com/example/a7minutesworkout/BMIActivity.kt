package com.example.a7minutesworkout

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import java.math.BigDecimal
import java.math.RoundingMode


//TODO(Step 15.2 : Creating a BMI calculator activity.)
//START
class BMIActivity : AppCompatActivity() {

    lateinit var tvYourBMI: TextView
    lateinit var tvBMIValue: TextView
    lateinit var tvBMIType: TextView
    lateinit var tvBMIDescription: TextView
    lateinit var etMetricUnitWeight: EditText
    lateinit var etMetricUnitHeight: EditText
    lateinit var llMetricUnitsView: LinearLayout
    lateinit var llUsUnitsView: LinearLayout
    lateinit var etUsUnitWeight: EditText
    lateinit var etUsUnitHeightFeet: EditText
    lateinit var etUsUnitHeightInch: EditText
    lateinit var rgUnits: RadioGroup


    //TODO(18.2 - Added variables for METRIC and US UNITS view and a variable for displaying the current selected view.
    var METRIC_UNITS_VIEW = "METRIC_UNIT_VIEW"
    var US_UNITS_VIEW = "US_UNITS_VIEW"

    var currentVisibleView: String = METRIC_UNITS_VIEW

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmiactivity)

        tvYourBMI = findViewById(R.id.tvYOurBMI)
        tvBMIValue = findViewById(R.id.tvBMIValue)
        tvBMIType = findViewById(R.id.tvBMIType)
        tvBMIDescription = findViewById(R.id.tvBMIDescription)
        etMetricUnitWeight = findViewById(R.id.etMetricUnitWeight)
        etMetricUnitHeight = findViewById(R.id.etMetricUnitHeight)
        llMetricUnitsView = findViewById(R.id.llMetricUnitsView)
        llUsUnitsView = findViewById(R.id.llUsUnitsView)
        etUsUnitWeight = findViewById(R.id.etUsUnitWeight)
        etUsUnitHeightFeet = findViewById(R.id.etUsUnitHeightFeet)
        etUsUnitHeightInch = findViewById(R.id.etUsUnitHeightInch)
        rgUnits = findViewById(R.id.rgUnits)


        //TODO(Step 15.5 : Setting up an action bar in BMI calculator activity using toolbar and adding a back arrow button along with click event.)
        //START

        var toolbar_bmi_activity = findViewById<Toolbar>(R.id.toolbar_bmi_activity)
        setSupportActionBar(toolbar_bmi_activity)

        val actionbar = supportActionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)   //set back button
        supportActionBar?.title = "CALCULATE BMI"   //setting a titlein the action bar.

        toolbar_bmi_activity.setNavigationOnClickListener{
            onBackPressed()
        }

        //TODO(18.5 - When the activity is launched make  METRIC UNITS VIEW visible.)
        makeVisibleMetricUnitsView()

        //TODO(18.6 - Adding a check change listener to the radio group and according to the radio button.)
        //Radio group change listener is set to the radio group which is added in XML.

        rgUnits.setOnCheckedChangeListener { group, checkedId ->
                //Here if checkId is METRIC UNITS view then make the view visible else US UNITS view.
            if(checkedId == R.id.rbMetricUnits){
                makeVisibleMetricUnitsView()
            }else{
                makeVisibleUsUnitsView()
            }
        }


        var btnCalculateUnits = findViewById<Button>(R.id.btnCalculateUnits)
        var etMetricUnitWeight = findViewById<EditText>(R.id.etMetricUnitWeight)
        var etMetricUnitHeight = findViewById<EditText>(R.id.etMetricUnitHeight)


        //TODO(16.3 - Adding a click event to METRIC UNIT Calculate button and after valid input calculating it.)
        //Button will calculate the input values in Metric Units.

        btnCalculateUnits.setOnClickListener{

            //TODO(19.2 - Handling the current visible and calculating US Units view input values if they are valid.)
            if(currentVisibleView == METRIC_UNITS_VIEW) {

                //The values are validated.
                if (validateMetricUnits()) {

                    //The height value is converted to a float value and divided by 100 to convert it to meter.
                    val heightValue: Float = etMetricUnitHeight.text.toString().toFloat() / 100

                    //The weight value is converted to a float value
                    val weightValue: Float = etMetricUnitWeight.text.toString().toFloat()

                    // BMI value is calculated in METRIC UNITS using the height and weight value.
                    val bmi = weightValue / (heightValue * heightValue)

                    displayBMIResult(bmi)
                } else {
                    Toast.makeText(
                        this@BMIActivity,
                        "please enter valid values.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {

                //THe values are validated.

                if(validateUsUnits()){

                    val usUnitHeightValueFeet: String =
                        etUsUnitHeightFeet.text.toString()  // Height Feet value entered in EditText component.

                    val usUnitHeightValueInch: String =
                        etUsUnitHeightInch.text.toString()  // Height Inch value entered in EditText component.

                    val usUnitWeightValue: Float =
                        etUsUnitWeight.text.toString().toFloat()    // Weight value entered in EditText component.

                    //Here the Height Feet and Inch values are merged and multiplied by 12 for converting it to inches.

                    val heightValue =
                        usUnitHeightValueInch.toFloat() + usUnitHeightValueFeet.toFloat()*12

                    val bmi = 703 * (usUnitWeightValue  / (heightValue * heightValue))

                    displayBMIResult(bmi)   // Displaying the result into UI
                }else{
                    Toast.makeText(
                        this@BMIActivity,
                        "Please enter valid values.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    //TODO(18.3 - Making a function to make the METRIC UNITS view visible.)
    /**
     * Function is used to make the METRIC UNITS VIEW visible and hide and hide the US UNITS view.
     */

    private fun makeVisibleMetricUnitsView(){
        currentVisibleView = METRIC_UNITS_VIEW  // Current view is updated here.
        llMetricUnitsView.visibility = View.VISIBLE // METRIC UNITS VIEW is visible.
        llUsUnitsView.visibility = View.GONE    // US UNITS VIEW is hidden

        etMetricUnitHeight.text!!.clear()   //height values is cleared if it is added.
        etMetricUnitWeight.text!!.clear()   // weight value is cleared if it is added.

        tvYourBMI.visibility = View.INVISIBLE   //Result is cleared and the labels are hidded
        tvYourBMI.visibility = View.INVISIBLE
        tvBMIType.visibility = View.INVISIBLE
        tvBMIDescription.visibility = View.INVISIBLE

    }

    // TODO(Step 18.4 : Making a function to make the US UNITS view visible.)
    // START
    private fun makeVisibleUsUnitsView() {
        currentVisibleView = US_UNITS_VIEW // Current View is updated here.
        llMetricUnitsView.visibility = View.GONE // METRIC UNITS VIEW is hidden
        llUsUnitsView.visibility = View.VISIBLE // US UNITS VIEW is Visible


        etUsUnitWeight.text!!.clear() // weight value is cleared.
        etUsUnitHeightFeet.text!!.clear() // height feet value is cleared.
        etUsUnitHeightInch.text!!.clear() // height inch is cleared.

        tvYourBMI.visibility = View.INVISIBLE // Result is cleared and the labels are hidden
        tvBMIValue.visibility = View.INVISIBLE // Result is cleared and the labels are hidden
        tvBMIType.visibility = View.INVISIBLE // Result is cleared and the labels are hidden
        tvBMIDescription.visibility = View.INVISIBLE // Result is cleared and the labels are hidden
    }

    //TODO(16.2 - Validating the metric units calculation input.)
    /**
     * Function is used to validate the input values for metric units.
     */

    private fun validateMetricUnits(): Boolean {
        var isValid = true;

        if(etMetricUnitWeight.text.toString().isEmpty())
            isValid = false
        else if(etMetricUnitHeight.text.toString().isEmpty())
            isValid = false

        return isValid
    }

    //TODO(19.1 - Validating the US Units view input values.)
    /**
     * Function is used to validate the input values for US Units.
     */

    private fun validateUsUnits(): Boolean {
        var isValid = true;

        if(etUsUnitWeight.text.toString().isEmpty())
            isValid = false
        else if(etUsUnitHeightFeet.text.toString().isEmpty())
            isValid = false
        else if(etUsUnitHeightInch.text.toString().isEmpty())
            isValid = false

        return isValid
    }



    //TODO(16.4 - Displaying the calculated BMI UI what we have designed earlier.)
    /**
     * Function is used to display the result of METRIC UNITS.
     */

    private fun displayBMIResult(bmi: Float){

        val bmiLabel: String
        val bmiDescription: String

        if(bmi.compareTo(15f) <= 0){
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0){
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops! You really need to take care of yourself! Eat more!"
        }else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0
        ) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0
        ) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        } else if (java.lang.Float.compare(bmi, 25f) > 0 && java.lang.Float.compare(
                bmi,
                30f
            ) <= 0
        ) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0
        ) {
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0
        ) {
            bmiLabel = "Obese Class || (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        } else {
            bmiLabel = "Obese Class ||| (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }


        tvYourBMI.visibility = View.VISIBLE
        tvBMIValue.visibility = View.VISIBLE
        tvBMIType.visibility = View.VISIBLE
        tvBMIDescription.visibility = View.VISIBLE

        //This is used to round the result value to 2 decimal values after "."
        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        tvBMIValue.text = bmiValue  //Value is set to textview
        tvBMIType.text = bmiLabel   //label is set to TextView
        tvBMIDescription.text = bmiDescription  //Description is set to textview.

    }
}