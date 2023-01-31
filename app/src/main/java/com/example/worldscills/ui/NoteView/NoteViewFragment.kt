package com.example.worldscills.ui.NoteView

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import com.example.worldscills.R
import com.example.worldscills.application.NotesApplication
import com.example.worldscills.databinding.FragmentFirstBinding
import com.example.worldscills.databinding.FragmentNoteViewBinding
import com.example.worldscills.module.Note
import com.example.worldscills.viewmodal.NoteViewModal
import java.util.Calendar

class NoteViewFragment : Fragment() {
    private var _binding: FragmentNoteViewBinding? = null
    private val binding get() = _binding!!
    private val noteViewModel: NoteViewModal by viewModels {
        NoteViewModal.NoteViewModelFactory((activity?.application as NotesApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNoteViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NewApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            val note: Note = arguments?.getSerializable("note") as Note
            Log.d("test", note.name)
            setUpData(note)
        }catch (e: Exception){
            val note = Note(0,"","",false,"",-1)
            setUpData(note)
            note.name = StringBuilder()
                .append(Calendar.getInstance().get(Calendar.YEAR))
                .append(".")
                .append(Calendar.getInstance().get(Calendar.MONTH))
                .append(".")
                .append(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).toString()
            noteViewModel.insert(note)
            noteViewModel.lastCreateId.observe(viewLifecycleOwner) {
                note.id = it.toInt()
            }
        }


    }
    fun setUpData(note: Note){
        binding.inputNote.setText(note.description)
        binding.editTextTextPersonName4.setText(note.name)
        binding.editTextTextPersonName4.doOnTextChanged { text, start, before, count ->
            run {
                note.name = text.toString()
                noteViewModel.updateOne(note)
            }
        }
        binding.inputNote.doOnTextChanged { text, start, before, count ->
            run {
                note.description = text.toString()
                noteViewModel.updateOne(note)
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}