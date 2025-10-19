package com.codeleg.noteflow.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.codeleg.noteflow.model.Note

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Update
    suspend fun updateNote(note:Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("Select * from notes order by id DESC")
    suspend fun getAllNotes():List<Note>

    @Query("SELECT * FROM notes WHERE title LIKE :query OR description LIKE :query ORDER BY id DESC")
    suspend fun searchNote(query: String?): List<Note>

}