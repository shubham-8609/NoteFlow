package com.codeleg.noteflow.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.codeleg.noteflow.R
import com.codeleg.noteflow.adapter.NoteAdapter
import com.codeleg.noteflow.database.NoteDao
import com.codeleg.noteflow.database.NoteDatabase
import com.codeleg.noteflow.databinding.FragmentHomeBinding
import com.codeleg.noteflow.model.Note
import com.codeleg.noteflow.utils.FragmentNavigation
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.internal.notify

class HomeFragment : Fragment(), NoteAdapter.OnNoteClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var noteRecyclerView: RecyclerView
    private lateinit var noteAdapter: NoteAdapter
    private var navigation: FragmentNavigation? = null
    private lateinit var ivNoNote: ImageView
    private lateinit var addNoteFab: FloatingActionButton
    private val  NoteDao: NoteDao by lazy { NoteDatabase.getDB(requireContext()).getNoteDao() }
    private var notes = mutableListOf<Note>()




    override fun onAttach(context: Context) {
        super.onAttach(context)
            navigation = context as? FragmentNavigation

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        addNoteFab = binding.addNotesFab
        ivNoNote = binding.ivNoNote
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        loadNotes()

        addNoteFab.setOnClickListener {
            Log.d("HomeFragment", "FloatingActionButton clicked")
            navigation?.navigateTo(AddNoteFragment(), null, true, "Add Note", R.menu.add_page_menu)
        }
    }

    private fun setupRecyclerView(){
        noteRecyclerView = binding.notesRecyclerView
        noteAdapter = NoteAdapter(requireContext(), notes, this)
        noteRecyclerView.adapter = noteAdapter
    }


    override fun onNoteClick(note: Note) {
        val bundle = Bundle().apply {
            putParcelable("note", note)
        }
        navigation?.navigateTo(EditFragment(), bundle, true , "Edit Note" , R.menu.edit_page_menu)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadNotes() {
        lifecycleScope.launch {
            val fetchedNotes = NoteDao.getAllNotes()
            if (fetchedNotes.isEmpty()){
                ivNoNote.visibility = View.VISIBLE
            }
           noteAdapter.updateNotes(fetchedNotes)
        }

    }
}
