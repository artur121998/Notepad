package com.example.speechtotext.models

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.speechtotext.R
import com.example.speechtotext.adapter.DatabaseHandler
import com.example.speechtotext.adapter.RecyclerViewAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class ItemTouchHelper(context: Context, private val record: ArrayList<Record>,private val dbHandler: DatabaseHandler,private val recyclerView: RecyclerView): ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    private var swipeBackground= ColorDrawable(Color.parseColor("#ff7539"))
    private val icon= ContextCompat.getDrawable(context, R.drawable.ic_delete_forever_black_24dp)

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val removePosition=viewHolder.adapterPosition
        val removeId=record[removePosition].id
        val removeName=record[removePosition].header
        val removeText=record[removePosition].text
        val removeDate=record[removePosition].date
        val removeTime=record[removePosition].time
        dbHandler.removeRecord(record[removePosition])
        record.removeAt(removePosition)
        (recyclerView.adapter as RecyclerViewAdapter).notifyItemRemoved(removePosition)
        Snackbar.make(viewHolder.itemView,"note deleted", Snackbar.LENGTH_LONG).setAction("UNDO"){
            val newRecord=Record(removeName,removeText,removeDate,removeTime)
            newRecord.id=removeId                   //добавляю в бд
            dbHandler.addRecord(newRecord)          //добавляю в список
            record.add(removePosition,newRecord)    //добавляю в ресайклвью
            (recyclerView.adapter as RecyclerViewAdapter).notifyItemInserted(removePosition)
        }.show()

    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {

        val itemView=viewHolder.itemView
        val iconMargin = (itemView.height - icon!!.intrinsicHeight) / 2
        val iconTop = itemView.top + (itemView.height - icon.intrinsicHeight) / 2
        val iconBottom = iconTop + icon.intrinsicHeight

        if (dX<0){
            val iconLeft = itemView.right - iconMargin - icon.intrinsicWidth
            val iconRight = itemView.right - iconMargin
            swipeBackground.setBounds(itemView.right+dX.toInt()-20,itemView.top,itemView.right,itemView.bottom)
            icon.setBounds(iconLeft,iconTop,iconRight,iconBottom)
        }else{
            swipeBackground.setBounds(0,0,0,0)
        }
        swipeBackground.draw(c)
        icon.draw(c)

        super.onChildDraw(
            c,
            recyclerView,
            viewHolder,
            dX,
            dY,
            actionState,
            isCurrentlyActive
        )
    }

}