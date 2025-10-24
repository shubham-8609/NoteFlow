package com.codeleg.noteflow.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.codeleg.noteflow.database.NoteDao
import com.codeleg.noteflow.database.NoteDatabase
import com.codeleg.noteflow.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.launch

class SharedNotesViewModel(application: Application) : AndroidViewModel(application) {

    private val noteDao: NoteDao by lazy { NoteDatabase.getDB(application).getNoteDao() }
    private var notes = mutableListOf<Note>()

    fun getNotes(): List<Note> = notes

    fun loadNotes(onLoaded: (List<Note>) -> Unit) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                noteDao.getAllNotes()
            }
            notes.clear()
            notes.addAll(result)
            onLoaded(result)
        }
    }
}
