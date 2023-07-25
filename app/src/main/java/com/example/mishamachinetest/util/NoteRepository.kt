package com.example.mishamachinetest.util

import androidx.lifecycle.LiveData
import com.example.mishamachinetest.db.NoteDao
import com.example.mishamachinetest.model.Note

class NoteRepository(
    private val noteDao: NoteDao
) {
    val allNotes: LiveData<List<Note>> = noteDao.getAllNotes()
    suspend fun insert(note: Note) {
        noteDao.insert(note)
    }
    suspend fun delete(note: ArrayList<Note>){
        noteDao.delete(note)
    }
}