package com.example.worldscills.application

import android.app.Application
import com.example.worldscills.database.NoteRoomDatabase
import com.example.worldscills.repository.NoteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class NotesApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { NoteRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { NoteRepository(database.noteDao()) }
}