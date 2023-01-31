package com.example.worldscills.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.worldscills.DiffCallback
import com.example.worldscills.R
import com.example.worldscills.helper.ItemTouchHelperAdapter
import com.example.worldscills.module.Note
import com.example.worldscills.viewmodal.NoteViewModal
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.log

class DragListAdapter(
    var list: ArrayList<Note>,
    val viewModal: NoteViewModal,
    private var optionsMenuClickListener: OptionsMenuClickListener,
    private var noteClickListener: NoteClickListener
) :
    RecyclerView.Adapter<DragListAdapter.ViewHolder>(), ItemTouchHelperAdapter{
    private val diffCallback = DiffCallback(list, ArrayList())

    interface OptionsMenuClickListener {
        fun onOptionsMenuClicked(note: Note,position: Int)
    }
    interface NoteClickListener {
        fun onNoteClickListener(note: Note, position: Int)
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameNote: TextView = itemView.findViewById<TextView>(R.id.noteName)
        val optionButton: TextView = itemView.findViewById(R.id.textViewOptions)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_list_note, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int = list.size

    fun submitList(updatedList: List<Note>) {
    diffCallback.newList = updatedList
    val diffResult = DiffUtil.calculateDiff(diffCallback)
//
    list.clear()
    list.addAll(updatedList.sortedBy { it.currentPosition })
    diffResult.dispatchUpdatesTo(this)
}

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            noteClickListener.onNoteClickListener(list[position], position)
        }
        with(holder){
            with(list[position]){
                nameNote.text = this.name
                optionButton.setOnClickListener {
                    optionsMenuClickListener.onOptionsMenuClicked(this,position)
                }
            }
        }

    }
    fun getItem(position: Int): Note = list[position];

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(list, i, i+1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
//                val note: Note = list[i]
//                val note2: Note = list[i-1]
//                note.currentPosition = i - 1
//                note2.currentPosition = i
//                viewModal.update(note)
//                viewModal.update(note2)
                Collections.swap(list, i, i-1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
        return true
    }
    fun itemMoved(from: Int, to: Int) {
        Collections.swap(list, from, to)
        notifyItemMoved(from, to)
    }
    override fun onItemDismiss(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
    }

}


