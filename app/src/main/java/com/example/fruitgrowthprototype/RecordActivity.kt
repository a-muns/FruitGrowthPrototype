package com.example.fruitgrowthprototype

import android.app.DatePickerDialog
import android.content.ContentValues
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fruitgrowthprototype.databinding.ActivityRecordBinding
import java.text.SimpleDateFormat
import java.util.*

class RecordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecordBinding
    val context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)

        binding = ActivityRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // DatePicker listener
        val myCalendar = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDisplay(myCalendar)
        }

        // dateButton listener (opens DatePicker)
        binding.recordDateButton.setOnClickListener {
            DatePickerDialog(
                this,
                datePicker,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        // Save values to DB as one row
        binding.recordSubmit.setOnClickListener() {
            val dateSet = binding.recordDateDisplay.text.toString()
            // Notify user if date is empty
            if (dateSet.equals(""))
                Toast.makeText(context, "Select a date", Toast.LENGTH_SHORT).show()
            else {
                var size1Data: Float
                var size2Data: Float
                var size3Data: Float
                var size4Data: Float
                var size5Data: Float
                try {
                    size1Data = binding.size1.text.toString().toFloat()
                } catch (e: Exception) {
                    size1Data = 0F
                }
                try {
                    size2Data = binding.size2.text.toString().toFloat()
                } catch (e: Exception) {
                    size2Data = 0F
                }
                try {
                    size3Data = binding.size3.text.toString().toFloat()
                } catch (e: Exception) {
                    size3Data = 0F
                }
                try {
                    size4Data = binding.size4.text.toString().toFloat()
                } catch (e: Exception) {
                    size4Data = 0F
                }
                try {
                    size5Data = binding.size5.text.toString().toFloat()
                } catch (e: Exception) {
                    size5Data = 0F
                }
                // Create Measurement object of entered data and store it in DB
                val measurement =
                    Measurement(dateSet, size1Data, size2Data, size3Data, size4Data, size5Data)
                val db = DBOpener(context)
                db.insertData(measurement)
            }
        }
    }

    // Display chosen date as dateDisplay
    private fun updateDisplay(myCalendar: Calendar) {
        val customFormat = "yyyy-MM-dd"
        val dateFormat = SimpleDateFormat(customFormat, Locale.CANADA)
        binding.recordDateDisplay.setText(dateFormat.format(myCalendar.time))
    }
}