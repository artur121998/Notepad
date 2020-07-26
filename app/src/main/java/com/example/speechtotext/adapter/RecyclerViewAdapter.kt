package com.example.speechtotext.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.speechtotext.R.layout
import com.example.speechtotext.models.Record
import kotlinx.android.synthetic.main.note_row.view.*
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter


class RecyclerViewAdapter(private val record: ArrayList<Record>, private val itemClickListener: RecyclerViewAdapter.OnItemClickListener) :
    RecyclerView.Adapter<RecyclerViewAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(p0.context)
        val cellOfRow = layoutInflater.inflate(layout.note_row, p0, false)
        return CustomViewHolder(
            cellOfRow
        )
    }

    override fun getItemCount(): Int {
        return record.size
    }


    override fun onBindViewHolder(p0: CustomViewHolder, p1: Int) {
        val note: Record = record[p1]
        val formatterDate =DateTimeFormatter.ofPattern("dd.MM.yyyy")
        p0.itemView.tvName.text = note.header
        p0.itemView.tvContent.text = note.text
        val date=LocalDate.ofEpochDay(note.date)
        p0.itemView.tvDate.text = date.format(formatterDate)
        p0.itemView.tvTime.text = note.time
        p0.bind(record[p1],itemClickListener)
    }

    fun removeItem(p0: RecyclerView.ViewHolder) {
        record.removeAt(p0.adapterPosition)
        notifyItemRemoved(p0.adapterPosition)
    }

    class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view){
        fun bind(record: Record,clickListener: OnItemClickListener){
            itemView.setOnClickListener {
                clickListener.onItemClicked(record)
            }
        }
    }

    fun updateData(viewModels: ArrayList<Record>) {
        record.clear()
        record.addAll(viewModels)
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClicked(record:Record) {

        }
    }
}