package com.example.mishamachinetest.util

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.mishamachinetest.db.NoteDatabase
import com.example.mishamachinetest.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    val allNotes: LiveData<List<Note>>
    val repository: NoteRepository

    init {
        val dao = NoteDatabase.getDatabase(application).getNotesDau()
        repository = NoteRepository(dao)
        allNotes = repository.allNotes
    }
    fun deleteNote(note: ArrayList<Note>) = viewModelScope.launch(Dispatchers.IO){
        repository.delete(note)
    }

    fun addNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(note)
    }
}