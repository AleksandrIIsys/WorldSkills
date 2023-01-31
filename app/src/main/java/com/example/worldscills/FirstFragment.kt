package com.example.worldscills

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.worldscills.adapter.DragListAdapter
import com.example.worldscills.application.NotesApplication
import com.example.worldscills.databinding.FragmentFirstBinding
import com.example.worldscills.helper.OnStartDragListener
import com.example.worldscills.helper.SimpleItemTouchHelperCallback
import com.example.worldscills.module.Note
import com.example.worldscills.viewmodal.NoteViewModal


class FirstFragment : Fragment(), OnStartDragListener {

    private var _binding: FragmentFirstBinding? = null
    lateinit var touchHelper : ItemTouchHelper;

    private val noteViewModel: NoteViewModal by viewModels {
        NoteViewModal.NoteViewModelFactory((activity?.application as NotesApplication).repository)
    }

    private val binding get() = _binding!!
    private var taskList = ArrayList<String>(listOf("HAHAHAH","KEK","123123123"));
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }
    private lateinit var adapter: DragListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.noteList.layoutManager = StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
//        binding.noteList.layoutManager = LinearLayoutManager(context);
        noteViewModel.allNotes.observe(viewLifecycleOwner) {note ->
            note.let { adapter.submitList(it)}

        }
        adapter = DragListAdapter(ArrayList(),viewModal = noteViewModel,object : DragListAdapter.OptionsMenuClickListener{
            // implement the required method
            override fun onOptionsMenuClicked(note: Note,position: Int) {

                performOptionsMenuClick(note, position)
            }
        }, object : DragListAdapter.NoteClickListener{
            override fun onNoteClickListener(note: Note, position: Int) {
                val bundle = Bundle()
                bundle.putSerializable("note", note)
                view.findNavController().navigate(R.id.action_FirstFragment_to_noteViewFragment, bundle);
            }
        });
        val callback: ItemTouchHelper.Callback = SimpleItemTouchHelperCallback(adapter, viewModal = noteViewModel, adapter)
        touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(binding.noteList)
        binding.noteList.adapter = adapter
        binding.createTask.setOnClickListener { l ->
            run {
                view.findNavController().navigate(R.id.action_FirstFragment_to_noteViewFragment);
            }
        }
    }
    private fun performOptionsMenuClick(note: Note, position: Int) {

        val popupMenu = PopupMenu(context , binding.noteList[position].findViewById(R.id.textViewOptions))
        // add the menu
        popupMenu.inflate(R.menu.options_menu)
        // implement on menu item click Listener
        popupMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener{
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                when(item?.itemId){
                    R.id.delete -> {
                        noteViewModel.delete(note)
                        adapter.notifyDataSetChanged()
                        Toast.makeText(context ,  "delete", Toast.LENGTH_SHORT).show()
                        return true
                    }
                    R.id.pin -> {
                        Toast.makeText(context ,  "pin", Toast.LENGTH_SHORT).show()
                        return true
                    }
                }
                return false
            }
        })
        popupMenu.show()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder?) {
        if (viewHolder != null) {
            touchHelper.startDrag(viewHolder)
        };
    }
}



