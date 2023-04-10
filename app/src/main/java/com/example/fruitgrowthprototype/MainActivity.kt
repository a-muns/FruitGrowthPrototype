package com.example.fruitgrowthprototype

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fruitgrowthprototype.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Redirect to RecordActivity
        binding.recordDataButton.setOnClickListener() {
            val intent = Intent(this, RecordActivity::class.java)
            startActivity(intent)
        }

        // Redirect to ViewActivity
        binding.viewDataButton.setOnClickListener() {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }

    }
}