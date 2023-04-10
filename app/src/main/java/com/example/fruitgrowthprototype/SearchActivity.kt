package com.example.fruitgrowthprototype

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.fruitgrowthprototype.databinding.ActivitySearchBinding
import java.text.SimpleDateFormat
import java.util.*

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    val context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        binding = ActivitySearchBinding.inflate(layoutInflater)
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
        binding.searchCalendarButton.setOnClickListener {
            DatePickerDialog(
                this,
                datePicker,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        // searchDateButton listener (with chosen date)
        binding.searchDateButton.setOnClickListener() {
            val dateToSend = binding.searchDateDisplay.text.toString()
            // Notify user if date is empty
            if (dateToSend.equals(""))
                Toast.makeText(context, "Select a date", Toast.LENGTH_SHORT).show()
            else {
                val intent = Intent(this, ViewActivity::class.java)
                intent.putExtra("date", dateToSend)
                startActivity(intent)
            }
        }

        // searchAllButton listener (returns all rows)
        binding.searchAllButton.setOnClickListener() {
            val intent = Intent(this, ViewActivity::class.java)
            intent.putExtra("date", "all")
            startActivity(intent)
        }
    }

    // Display chosen date as dateDisplay
    private fun updateDisplay(myCalendar: Calendar) {
        val customFormat = "yyyy-MM-dd"
        val dateFormat = SimpleDateFormat(customFormat, Locale.CANADA)
        binding.searchDateDisplay.setText(dateFormat.format(myCalendar.time))
    }
}