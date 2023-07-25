package com.example.mishamachinetest.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mishamachinetest.R
import com.example.mishamachinetest.adapter.NoteRVAdapter
import com.example.mishamachinetest.databinding.ActivityMainBinding
import com.example.mishamachinetest.model.Note
import com.example.mishamachinetest.util.NoteViewModel
import com.example.mishamachinetest.util.Swipe

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: NoteViewModel
    lateinit var binding: ActivityMainBinding
    lateinit var allNotes: ArrayList<Note>
    lateinit var noteRV: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Misha Note App"
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        noteRV = findViewById(R.id.idRVNotes)
        allNotes = ArrayList()
        binding.idRVNotes.layoutManager = LinearLayoutManager(this)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.reverseLayout = true
        linearLayoutManager.stackFromEnd = true
        noteRV.layoutManager = linearLayoutManager
        val noteRVAdapter = NoteRVAdapter(this, this)
        binding.idRVNotes.adapter = noteRVAdapter
        viewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(
            NoteViewModel::class.java
        )
        viewModel.allNotes.observe(this, Observer { list ->
            list.let {
                noteRVAdapter.updateList(it)
            }
        })
        binding.idFABAddNote.setOnClickListener {
            val intent = Intent(this, AddNote::class.java)
            startActivity(intent)
        }
        //swipe to delete method
        val swipe = object : Swipe() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                when (direction) {
                    ItemTouchHelper.LEFT -> {

                        noteRVAdapter.deleteItem(viewHolder.adapterPosition)

                    }
                }

                super.onSwiped(viewHolder, direction)
            }
        }
        val touchHelper = ItemTouchHelper(swipe)
        touchHelper.attachToRecyclerView(noteRV)
    }

    fun onNoteClick(note: Note) {
        val intent = Intent(this, NoteDetail::class.java)
        intent.putExtra("noteType", "Show")
        intent.putExtra("noteTitle", note.noteTitle)
        intent.putExtra("noteDescription", note.noteDescription)
        intent.putExtra("noteID", note.id)
        intent.putExtra("noteDate", note.timeStamp)
        startActivity(intent)
    }
}