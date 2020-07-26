package com.example.speechtotext.models

import android.content.Context
import com.example.speechtotext.MainActivity
import com.example.speechtotext.adapter.DatabaseHandler
import com.jakewharton.threetenabp.AndroidThreeTen
import org.threeten.bp.LocalDate
import org.threeten.bp.Period

class DateFilter(val context: Context, private val mainActivity: MainActivity) {
    private val dbHandler = DatabaseHandler(context)

    fun period(filter: Int) {
        AndroidThreeTen.init(context)
        val period = Period.of(0, 0, filter)
        val dateToday = LocalDate.now()
        val date = dateToday.plus(period)
        val dateLong = date.toEpochDay()
        val record = dbHandler.getFilterRecord(dateLong)
        mainActivity.updateItem(record)
    }

    fun reset(){
        val record=dbHandler.getAllRecord()
        mainActivity.updateItem(record)
    }
}