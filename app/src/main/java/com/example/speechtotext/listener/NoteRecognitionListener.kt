package com.example.speechtotext.listener

import android.content.Context
import android.os.Bundle
import android.speech.SpeechRecognizer
import android.widget.EditText
import com.example.speechtotext.R
import com.google.android.material.textfield.TextInputEditText
import java.util.*
import kotlin.collections.ArrayList

class NoteRecognitionListener(
    private val context: Context,
    private val etName: EditText,
    private val textInput: TextInputEditText
) : CustomRecognitionListener() {

    override fun onResults(p0: Bundle?) {
        val speechName=context.resources.getString(R.string.SpeechName)
        val speechText=context.resources.getString(R.string.SpeechText)
        val regName = Regex(pattern = speechName)
        val regText = Regex(pattern = speechText)
        val result: ArrayList<String>? =
            p0?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
        val res = result?.get(0).toString()
        res.toLowerCase(Locale.ROOT)
        val matchedName = regName.containsMatchIn(input = res)
        val matchedText = regText.containsMatchIn(input = res)
        if (matchedName) {
            if (matchedText) {
                var name = res.substring(res.indexOf(speechName), res.indexOf(speechText))
                name = name.substringAfter(' ')
                var text = res.substring(res.indexOf(speechText))
                text = text.substringAfter(' ')
                etName.setText(name)
                textInput.setText(text)
            }
        }

    }
}