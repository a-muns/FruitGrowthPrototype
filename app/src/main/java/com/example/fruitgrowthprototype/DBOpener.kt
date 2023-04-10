package com.example.fruitgrowthprototype

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

// Globals
private val DATABASE_NAME = "DB"
private val VERSION_NUM = 1

class DBOpener(var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, VERSION_NUM) {

    // Holds constant (final and static) values
    companion object {
        const val TABLE_NAME = "AppleSize"
        const val COL_ID = "appleSizeID"
        const val COL_DATE = "date" // Format: YYYY-MM-DD
        const val COL_SIZE1 = "size1"
        const val COL_SIZE2 = "size2"
        const val COL_SIZE3 = "size3"
        const val COL_SIZE4 = "size4"
        const val COL_SIZE5 = "size5"
    }

    // Create table and columns on DB creation
    override fun onCreate(db: SQLiteDatabase?) {
        val sql = ("CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_DATE + " DATE, " +
                COL_SIZE1 + " REAL NULL, " +
                COL_SIZE2 + " REAL NULL, " +
                COL_SIZE3 + " REAL NULL, " +
                COL_SIZE4 + " REAL NULL, " +
                COL_SIZE5 + " REAL NULL);")
        db?.execSQL(sql) ?: ""
    }

    // Drop and create new DB on upgrade
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Drop current DB
        val sql = ("DROP TABLE IF EXISTS " + TABLE_NAME)
        db?.execSQL(sql) ?: ""
        // Create new DB
        onCreate(db)
    }

    // Inserts a row of values into the DB (for RecordActivity)
    fun insertData (measurement: Measurement) {
        val db = this.writableDatabase
        val newRowValues = ContentValues()
        newRowValues.put(COL_DATE, measurement.date)
        newRowValues.put(COL_SIZE1, measurement.size1)
        newRowValues.put(COL_SIZE2, measurement.size2)
        newRowValues.put(COL_SIZE3, measurement.size3)
        newRowValues.put(COL_SIZE4, measurement.size4)
        newRowValues.put(COL_SIZE5, measurement.size5)
        val result = db.insert(TABLE_NAME, null, newRowValues)
        if (result <= -1)
            Toast.makeText(context, "Not saved.", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(context, "Saved.", Toast.LENGTH_SHORT).show()
    }

    fun loadDBData(date: String, array: ArrayList<String>) {
        val db = this.readableDatabase
        val results: Cursor
        // If searchAllButton was clicked
        if (date.equals("all")) {
            results = db.rawQuery("SELECT * FROM " + TABLE_NAME + ";", null)
        }
        else { // If a date was selected
            results = db.rawQuery(
                "SELECT * FROM " + TABLE_NAME +
                        " WHERE " + COL_DATE + " LIKE \'" + date + "\';", null
            )
        }

        // Column indexes
        // val idColIndex = results.getColumnIndex(COL_ID)
        val dateColIndex = results.getColumnIndex(COL_DATE)
        val size1ColIndex = results.getColumnIndex(COL_SIZE1)
        val size2ColIndex = results.getColumnIndex(COL_SIZE2)
        val size3ColIndex = results.getColumnIndex(COL_SIZE3)
        val size4ColIndex = results.getColumnIndex(COL_SIZE4)
        val size5ColIndex = results.getColumnIndex(COL_SIZE5)

        // Add each row of data to the ArrayList in ViewActivity
        while (results.moveToNext()) {
            try {
                // val id = results.getInt(idColIndex)
                val date = results.getString(dateColIndex)
                val size1 = results.getFloat(size1ColIndex)
                val size2 = results.getFloat(size2ColIndex)
                val size3 = results.getFloat(size3ColIndex)
                val size4 = results.getFloat(size4ColIndex)
                val size5 = results.getFloat(size5ColIndex)
                array.add(date + ": " + size1 + " | " + size2 + " | " + size3 + " | " + size4 + " | " + size5)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        // If no results returned
        if (array.size == 0) {
            array.add("No records found.")
        }
        results.close()
    }
}