package com.example.speechtotext.listener

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.SpeechRecognizer
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.speechtotext.CreateNote
import com.example.speechtotext.MainActivity
import com.example.speechtotext.R
import com.example.speechtotext.adapter.DatabaseHandler
import com.jakewharton.threetenabp.AndroidThreeTen
import com.example.speechtotext.adapter.RecyclerViewAdapter
import com.example.speechtotext.adapter.RecyclerViewAdapter.OnItemClickListener
import com.example.speechtotext.models.DateFilter
import kotlinx.android.synthetic.main.activity_main.*
import org.threeten.bp.LocalDate
import org.threeten.bp.Period
import org.threeten.bp.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class MainRecognitionListener(val context: Context, private val mainActivity: MainActivity) :
    CustomRecognitionListener() {

    private val dbHandler = DatabaseHandler(context)

    override fun onResults(p0: Bundle?) {
        val dateFilter=DateFilter(context,mainActivity)
        val result: ArrayList<String>? =
            p0?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
        val res = result?.get(0).toString().toLowerCase(Locale.ROOT)
        val regexCreate = Regex(pattern = context.resources.getString(R.string.SpeechCreate))
        val regexFilter = Regex(pattern = context.resources.getString(R.string.SpeechFilter))
        val regexReset = Regex(pattern = context.resources.getString(R.string.SpeechReset))
        if (regexReset.containsMatchIn(res)){
            dateFilter.reset()
        }
        if (regexCreate.containsMatchIn(input = res)) {
            val intent = Intent(context, CreateNote::class.java)
            intent.putExtra("Rewrite", false)
            startActivity(context, intent, null)
        }
        if (regexFilter.containsMatchIn(input = res)) {

            val regexThreeDay =
                Regex(pattern = context.resources.getString(R.string.SpeechFilterThree))
            val regexThreeDayNumber =
                Regex(pattern = context.resources.getString(R.string.SpeechFilterThreeNumber))
            val regexWeek =
                Regex(pattern = context.resources.getString(R.string.SpeechFilterWeek))
            val regexWeekNumber =
                Regex(pattern = context.resources.getString(R.string.SpeechFilterWeekNumber))
            if (regexThreeDay.containsMatchIn(input = res) || regexThreeDayNumber.containsMatchIn(
                    input = res
                )
            ) {
                dateFilter.period(-3)
            }
            if (regexWeek.containsMatchIn(input = res) || regexWeekNumber.containsMatchIn(input = res)) {
                dateFilter.period(-7)
            }
        }


    }



}
