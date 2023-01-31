package com.example.worldscills.viewmodal

import android.view.View
import androidx.lifecycle.*
import com.example.worldscills.module.Note
import com.example.worldscills.repository.NoteRepository
import kotlinx.coroutines.*

class NoteViewModal(private val repository: NoteRepository) : ViewModel() {

    val allNotes: LiveData<List<Note>> = repository.allNotes.asLiveData();
    var lastCreateId: MutableLiveData<Long> = MutableLiveData()

    fun insert(note: Note) = viewModelScope.launch {
       lastCreateId.postValue(repository.insert(note))
    }
    fun update(note: List<Note>) = viewModelScope.launch {
        repository.update(note)
    }
    fun updateOne(note: Note) = viewModelScope.launch {
        repository.updateOne(note)
    }
    fun delete(note: Note) = viewModelScope.launch {
        repository.delete(note)
    }
class NoteViewModelFactory(private val repository: NoteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModal::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NoteViewModal(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}}

