package com.example.mishamachinetest.activities

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.example.mishamachinetest.MainActivity
import com.example.mishamachinetest.R
import com.example.mishamachinetest.databinding.ActivityAddNoteBinding
import com.example.mishamachinetest.model.Note
import com.example.mishamachinetest.util.NoteViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.Date

class AddNote : AppCompatActivity() {
    lateinit var binding: ActivityAddNoteBinding
    lateinit var noteTitleEdt: EditText
    lateinit var noteDescriptionEdt: EditText
    lateinit var viewModel: NoteViewModel
    private lateinit var dataBase: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        title = "Add Note"
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        noteTitleEdt = findViewById(R.id.idEdtNoteTitle)
        noteDescriptionEdt = findViewById(R.id.idEdtNoteDescription)
        dataBase = FirebaseDatabase.getInstance().getReference("Notes")
        viewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[NoteViewModel::class.java]
        binding.btnSave.setOnClickListener {
            val noteTitle = noteTitleEdt.text.toString()
            val noteDescription = noteDescriptionEdt.text.toString()
            if (noteTitle.isNotEmpty() && noteDescription.isNotEmpty()) {
                val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                val currentDate: String = sdf.format(Date())
                viewModel.addNote(Note(noteTitle, noteDescription, currentDate))
                val addNote = Note(noteTitle, noteDescription, currentDate)
                val noteTitle = dataBase.push().key!!
                dataBase.child(noteTitle).setValue(addNote).addOnCompleteListener {
                    noteTitleEdt.text.toString()
                    noteDescriptionEdt.text.toString()
                }.addOnFailureListener { err ->
                    Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_SHORT).show()
                }
                Toast.makeText(this, "Note Added", Toast.LENGTH_SHORT).show()
            }
            startActivity(Intent(applicationContext, MainActivity::class.java))
            this.finish()
        }
        binding.btnBold.setOnClickListener {
            buttonBold()
        }
        binding.btnItalic.setOnClickListener {
            buttonItalics()
        }
        binding.btnUnderline.setOnClickListener {
            buttonUnderline()
        }

    }

    private fun buttonBold() {
        val spannableString: Spannable = SpannableStringBuilder(noteDescriptionEdt.text)
        spannableString.setSpan(
            StyleSpan(Typeface.BOLD),
            noteDescriptionEdt.selectionStart,
            noteDescriptionEdt.selectionEnd,
            0
        )
        noteDescriptionEdt.setText(spannableString)
    }

    private fun buttonItalics() {
        val spannableString: Spannable = SpannableStringBuilder(noteDescriptionEdt.text)
        spannableString.setSpan(
            StyleSpan(Typeface.ITALIC),
            noteDescriptionEdt.selectionStart,
            noteDescriptionEdt.selectionEnd,
            0
        )
        noteDescriptionEdt.setText(spannableString)
    }

    private fun buttonUnderline() {
        val spannableString: Spannable = SpannableStringBuilder(noteDescriptionEdt.text)
        spannableString.setSpan(
            UnderlineSpan(),
            noteDescriptionEdt.selectionStart,
            noteDescriptionEdt.selectionEnd,
            0
        )
        noteDescriptionEdt.setText(spannableString)
    }

}