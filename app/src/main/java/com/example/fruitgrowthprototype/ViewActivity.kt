package com.example.fruitgrowthprototype

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.fruitgrowthprototype.databinding.ActivitySearchBinding
import com.example.fruitgrowthprototype.databinding.ActivityViewBinding

class ViewActivity : AppCompatActivity() {

    // Globals
    private lateinit var binding: ActivityViewBinding
    val context = this
    var measurementArray = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)

        binding = ActivityViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Receive date from SearchArctivity after resetting its value
        val dateReceived = intent.getStringExtra("date").toString()

        // Get data from database
        val db = DBOpener(context)
        db.loadDBData(dateReceived, measurementArray)

        // Set adapter
        val listAdapter = ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, measurementArray)
        binding.listView.adapter = listAdapter
    }
}