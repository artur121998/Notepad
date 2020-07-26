package com.example.speechtotext.adapter

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.speechtotext.models.Record
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class
DatabaseHandler(context: Context):SQLiteOpenHelper(context,DB_NAME,null,DB_VERSION) {

    companion object{
        private const val DB_NAME="test3"
        private const val DB_VERSION=1
        private const val TABLE_NAME="record"
        private const val ID="id"
        private const val HEADER="Header"
        private const val TEXT="Text"
        private const val DATE="Date"
        private const val TIME="Time"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val CREATE_TABLE = "CREATE TABLE $TABLE_NAME " +
                "($ID Integer PRIMARY KEY, $HEADER TEXT, $TEXT TEXT,$DATE LONG,$TIME TEXT)"
        p0?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    fun addRecord(record: Record):Boolean{
        val db =this.writableDatabase
        val values=ContentValues()
        values.put(HEADER,record.header)
        values.put(TEXT,record.text)
        values.put(DATE,record.date)
        values.put(TIME,record.time)
        val success=db.insert(TABLE_NAME,null,values)
        db.close()
        return (Integer.parseInt("$success")!=-1)
    }

    fun removeRecord(record: Record):Boolean{
        val db=this.writableDatabase
        val success=db.delete(TABLE_NAME,"$ID = ${record.id}",null)
        db.close()
        return (Integer.parseInt("$success")!=-1)
    }

    fun updateRecord(record: Record):Boolean{
        val db=this.writableDatabase
        val values=ContentValues()
        values.put(HEADER,record.header)
        values.put(TEXT,record.text)
        values.put(DATE,record.date)
        values.put(TIME,record.time)
        val success=db.update(TABLE_NAME,values,"$ID=${record.id}",null)
        db.close()
        return (Integer.parseInt("$success")!=-1)
    }

    fun getAllRecord():ArrayList<Record>{
        val selectAllQuery="SELECT * FROM $TABLE_NAME"
        return getRecord(selectAllQuery)
    }

    fun getFilterRecord(date:Long):ArrayList<Record>{
        val selectFilterQuery="SELECT * FROM $TABLE_NAME WHERE $DATE > $date"
        return getRecord(selectFilterQuery)
    }

    private fun getRecord(selectQuery:String):ArrayList<Record>{
        val records=ArrayList<Record>()
        val db=readableDatabase
        val cursor=db.rawQuery(selectQuery,null)
        if (cursor.count!=0 && cursor.moveToFirst()) {
            do {
                val header= cursor.getString(cursor.getColumnIndex(HEADER))
                val text = cursor.getString(cursor.getColumnIndex(TEXT))
                val date = cursor.getLong(cursor.getColumnIndex(DATE))
                val time = cursor.getString(cursor.getColumnIndex(TIME))
                val record=Record(header,text,date,time)
                record.id = cursor.getInt(cursor.getColumnIndex(ID))
                records.add(record)
            }while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return records
    }

}