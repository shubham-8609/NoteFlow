package com.codeleg.noteflow.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.codeleg.noteflow.model.Note

@Database(entities = [Note::class], version = 1, exportSchema = false )
abstract class NoteDatabase : RoomDatabase() {

    abstract fun getNoteDao(): NoteDao

    companion object{

        private const val DATABASE_NAME = "note_database"

        @Volatile
        private var instance: NoteDatabase? = null

        fun getDB(context: Context): NoteDatabase {

            return instance ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    DATABASE_NAME

                )
                    .fallbackToDestructiveMigration()
                    .build()
                instance
            }
        }


    }

}