package com.example.worldscills.module

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "note_table")
class Note(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "isPinned") val isPinned: Boolean,
    @ColumnInfo(name = "color") var color: String,
    @ColumnInfo(name = "currentPosition") var currentPosition: Int,
) : Serializable