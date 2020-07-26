package com.example.speechtotext

import HelpSheetDialogFragment
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.speechtotext.adapter.DatabaseHandler
import com.example.speechtotext.adapter.RecyclerViewAdapter
import com.example.speechtotext.fragment.BottomSheetDialogFragment
import com.example.speechtotext.listener.MainRecognitionListener
import com.example.speechtotext.models.Record
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity(),RecyclerViewAdapter.OnItemClickListener {

    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var record:ArrayList<Record>
    lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private lateinit var dbHandler:DatabaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(bottom_app_bar)
        dbHandler=DatabaseHandler(this)
        record=dbHandler.getAllRecord()
        linearLayout(record)
        fab.setOnClickListener {
            startListeningWithoutDialog()
        }
        swipeDelete()
    }



    private fun swipeDelete(){
        val itemTouchHelperCallback=com.example.speechtotext.models.ItemTouchHelper(this,record,dbHandler,recycler_view_main)
        val itemTouchHelper=ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recycler_view_main)
    } //удаление свайпом влево

    private fun linearLayout(arrayRecord:ArrayList<Record>){
        linearLayoutManager=LinearLayoutManager(this)
        linearLayoutManager.reverseLayout=true
        linearLayoutManager.stackFromEnd=true
        recycler_view_main.layoutManager = linearLayoutManager
        recyclerViewAdapter=RecyclerViewAdapter(arrayRecord,this)
        recycler_view_main.adapter =recyclerViewAdapter
    } // подгружение всех item в recyclerview


    fun updateItem(arrayRecord: ArrayList<Record>){
        recyclerViewAdapter.updateData(arrayRecord)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        val inflater = menuInflater
        inflater.inflate(R.menu.app_bar_bottom, menu)
        return true
    } //меню

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.app_help -> {
                val helpSheetDialogFragment=HelpSheetDialogFragment()
                helpSheetDialogFragment.show(supportFragmentManager,helpSheetDialogFragment.tag)
            }
            android.R.id.home -> {
                val bottomNavDrawerFragment =
                    BottomSheetDialogFragment(this)
                bottomNavDrawerFragment.show(supportFragmentManager, bottomNavDrawerFragment.tag)
            }
            R.id.app_add->{
                val intent = Intent(this, CreateNote::class.java)
                intent.putExtra("Rewrite",false)
                startActivity(intent)
            }
        }

        return true
    } // меню

    private fun startListeningWithoutDialog() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5)
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, this.packageName)
        val listener =
            MainRecognitionListener(this,this)
        val sr = SpeechRecognizer.createSpeechRecognizer(this)
        sr.setRecognitionListener(listener)
        sr.startListening(intent)
    } //микрофон

    override fun onItemClicked(record: Record) {
        val intent=Intent(this,CreateNote::class.java)
        intent.putExtra("Item",record)
        intent.putExtra("Rewrite",true)
        startActivity(intent)
    }

    private fun toast(str: String) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
    }

}
