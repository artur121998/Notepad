package com.example.speechtotext

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.speechtotext.adapter.DatabaseHandler
import com.example.speechtotext.listener.NoteRecognitionListener
import com.example.speechtotext.models.Record
import com.jakewharton.threetenabp.AndroidThreeTen
import kotlinx.android.synthetic.main.activity_create_note.*
import kotlinx.android.synthetic.main.activity_create_note.bottom_app_bar
import kotlinx.android.synthetic.main.activity_create_note.fab
import kotlinx.android.synthetic.main.activity_main.*
import org.threeten.bp.format.DateTimeFormatter
import java.util.*
import kotlin.properties.Delegates

class CreateNote : AppCompatActivity() {

    private lateinit var dbHandler: DatabaseHandler
    private lateinit var record: Record
    private var rewrite by Delegates.notNull<Boolean>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_note)
        rewrite = intent.extras?.getBoolean("Rewrite")!!
        setSupportActionBar(bottom_app_bar)
        AndroidThreeTen.init(this)
        dbHandler = DatabaseHandler(this)
        fab.setOnClickListener {
            startListeningWithoutDialog()
        }
        if (rewrite) {
            record= intent.extras?.getSerializable("Item") as Record
            etName.setText(record.header)
            textInput.setText(record.text)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu);
        val inflater = menuInflater
        inflater.inflate(R.menu.app_bar_bottom_secondary, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.app_delete -> toast("Fav menu item is clicked!")
            R.id.app_save -> onSave()
        }

        return true
    }

    private fun onSave() {
        if (etName.text.toString() != "" && textInput.text.toString() != "") {

            val success: Boolean
            val header = etName.text.toString()
            val text = textInput.text.toString()
           //val formatterDate = DateTimeFormatter.ofPattern("dd.MM.yyyy")
            val formatterTime = DateTimeFormatter.ofPattern("HH:mm:ss")
            val date = org.threeten.bp.LocalDate.now()
            val time = org.threeten.bp.LocalTime.now().format(formatterTime)

            success = if (rewrite) {
                record.rewrite(header, text, date.toEpochDay(), time)
                dbHandler.updateRecord(record)
            } else {
                val record = Record(header, text, date.toEpochDay(), time)
                dbHandler.addRecord(record)
            }
            if (success) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }


    private fun startListeningWithoutDialog() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5)
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, this.packageName)
        val listener =
            NoteRecognitionListener(
                this,
                etName = etName,
                textInput = textInput
            )
        val sr = SpeechRecognizer.createSpeechRecognizer(this)
        sr.setRecognitionListener(listener)
        sr.startListening(intent)
    }


    private fun toast(str: String) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
    }

}
