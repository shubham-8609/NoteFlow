package com.codeleg.noteflow.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.codeleg.noteflow.adapter.NoteAdapter
import com.codeleg.noteflow.database.NoteDao
import com.codeleg.noteflow.database.NoteDatabase
import com.codeleg.noteflow.databinding.FragmentHomeBinding
import com.codeleg.noteflow.model.Note
import com.codeleg.noteflow.utils.FragmentNavigation
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.internal.notify

class HomeFragment : Fragment(), NoteAdapter.OnNoteClickListener {

    private var homeBinding: FragmentHomeBinding? = null
    private val binding get() = homeBinding!!
    private lateinit var noteRecyclerView: RecyclerView
    private lateinit var noteAdapter: NoteAdapter
    private var navigation: FragmentNavigation? = null
    private lateinit var addNoteFab: FloatingActionButton
    private lateinit var NoteDao: NoteDao
    private lateinit var NoteDB: NoteDatabase
    private var notes = mutableListOf<Note>()




    override fun onAttach(context: Context) {
        super.onAttach(context)
            if (context is FragmentNavigation) {
                navigation = context
            }
        NoteDB = NoteDatabase.getDB(requireContext())
        NoteDao = NoteDB.getNoteDao()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteRecyclerView = binding.notesRecyclerView
        addNoteFab = binding.addNotesFab

        noteAdapter = NoteAdapter(requireContext(), notes, this)
        noteRecyclerView.adapter = noteAdapter

        // Load notes
        lifecycleScope.launch {
            val fetchedNotes = NoteDao.getAllNotes()
            notes.clear()
            notes.addAll(fetchedNotes)
            noteAdapter.notifyDataSetChanged()
        }

        addNoteFab.setOnClickListener {
            navigation?.navigateTo(AddNoteFragment(), null, true)
        }
    }


    override fun onNoteClick(note: Note) {
        val bundle = Bundle().apply {
            putParcelable("note", note)
        }
        navigation?.navigateTo(EditFragment(), bundle, true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        homeBinding = null

    }
/*
    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            val fetchedNotes = NoteDao.getAllNotes()
            notes.clear()
            notes.addAll(fetchedNotes)
            noteAdapter.notifyDataSetChanged()
        }
    }*/
}
