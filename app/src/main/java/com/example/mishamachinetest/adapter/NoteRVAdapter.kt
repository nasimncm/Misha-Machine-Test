package com.example.mishamachinetest.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mishamachinetest.MainActivity
import com.example.mishamachinetest.R
import com.example.mishamachinetest.model.Note

class NoteRVAdapter(
    val context: Context, private val noteClickInterface: MainActivity
) : RecyclerView.Adapter<NoteRVAdapter.ViewHolder>() {
    private val allNotes = ArrayList<Note>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val noteTV = itemView.findViewById<TextView>(R.id.idTVNoteTitle)
        val timeTV = itemView.findViewById<TextView>(R.id.idTVTimeStamp)
        val checkBox = itemView.findViewById<CheckBox>(R.id.checkBox)
    }

    fun deleteItem(i: Int) {
        allNotes.removeAt(i)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_rv_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return allNotes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.noteTV.text = allNotes[position].noteTitle
        holder.timeTV.text = "Created at : " + allNotes[position].timeStamp

        holder.itemView.setOnClickListener {
            noteClickInterface.onNoteClick(allNotes[position])
        }
        holder.checkBox.setOnClickListener {
            holder.noteTV.setTextColor(ContextCompat.getColor(context, R.color.gray))
            holder.timeTV.setTextColor(ContextCompat.getColor(context, R.color.gray))

        }
    }

    fun updateList(newList: List<Note>) {
        allNotes.clear()
        allNotes.addAll(newList)
        notifyDataSetChanged()
    }
}