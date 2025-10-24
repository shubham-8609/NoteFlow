package com.codeleg.noteflow

import android.app.Application
import com.codeleg.noteflow.database.NoteDatabase

class NoteFlowApp: Application() {

    val database: NoteDatabase by lazy {
        NoteDatabase.getDB(this)
    }


}