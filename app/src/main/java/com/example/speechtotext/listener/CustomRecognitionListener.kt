package com.example.speechtotext.listener

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.view.Gravity
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.example.speechtotext.CreateNote
import com.example.speechtotext.MainActivity
import com.example.speechtotext.R
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_create_note.*
import java.util.*


open class CustomRecognitionListener() : RecognitionListener {
    private val TAG = "RecognitionListener"
    override fun onReadyForSpeech(p0: Bundle?) {
        Log.d(TAG, "onReadyForSpeech")
    }

    override fun onRmsChanged(p0: Float) {
        Log.d(TAG, "onRmsChanged")
    }

    override fun onBufferReceived(p0: ByteArray?) {
        Log.d(TAG, "onBufferReceived");
    }

    override fun onPartialResults(p0: Bundle?) {
        Log.d(TAG, "onPartialResults");
    }

    override fun onEvent(p0: Int, p1: Bundle?) {
        Log.d(TAG, "onEvent $p0");
    }

    override fun onBeginningOfSpeech() {
        Log.d(TAG, "onBeginningOfSpeech")
    }

    override fun onEndOfSpeech() {
        Log.d(TAG, "onEndofSpeech");
    }

    override fun onError(p0: Int) {

    }

    override fun onResults(p0: Bundle?) {

    }


}