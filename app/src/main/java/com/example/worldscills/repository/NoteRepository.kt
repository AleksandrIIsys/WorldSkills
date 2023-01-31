package com.example.worldscills.repository

import androidx.annotation.WorkerThread
import com.example.worldscills.dao.NoteDao
import com.example.worldscills.module.Note
import kotlinx.coroutines.flow.Flow

class NoteRepository(private val noteDao: NoteDao) {

    val allNotes: Flow<List<Note>> = noteDao.getNote()
    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(note: Note): Long {
       return noteDao.insert(note)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(note: List<Note>) {
        noteDao.update(note)
    }
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateOne(note: Note) {
        noteDao.updateOne(note)
    }
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(note: Note) {
        noteDao.delete(note)
    }
}