package com.example.speechtotext.models

import android.icu.util.LocaleData
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import java.io.Serializable
import java.sql.Time
import java.util.*

class Record(var header:String, var text:String, var date:Long, var time:String):Serializable {
        var id=0

        fun rewrite(header: String,text: String,date: Long,time: String){
                this.header=header
                this.text=text
                this.date=date
                this.time=time
        }
}