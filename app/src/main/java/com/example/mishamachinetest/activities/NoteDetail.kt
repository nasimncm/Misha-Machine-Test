package com.example.mishamachinetest.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.example.mishamachinetest.R
import com.example.mishamachinetest.databinding.ActivityNoteDetailBinding

class NoteDetail : AppCompatActivity() {
    lateinit var binding: ActivityNoteDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Note Details"
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val noteType = intent.getStringExtra("noteType")
        if (noteType.equals("Show")){
            val noteTitle = intent.getStringExtra("noteTitle")
            val noteDesc = intent.getStringExtra("noteDescription")
            val noteTime = intent.getStringExtra("noteDate")
            binding.tvTitle.text = noteTitle
            binding.tvDescription.text = noteDesc
            binding.tvTimeDate.text = noteTime
        }
    }
}