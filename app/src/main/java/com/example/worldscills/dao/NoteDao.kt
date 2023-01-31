package com.example.worldscills.dao

import androidx.room.*
import com.example.worldscills.module.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM note_table")
    fun getNote(): Flow<List<Note>>

    @Insert
    suspend fun insert(note: Note): Long

    @Update
    suspend fun update(note: List<Note>): Int
    @Update
    suspend fun updateOne(note: Note): Int

    @Query("DELETE FROM note_table")
    suspend fun deleteAll(): Int

    @Delete
    suspend fun delete(note: Note)
}
