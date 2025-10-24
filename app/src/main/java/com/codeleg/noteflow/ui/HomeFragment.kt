package com.codeleg.noteflow.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.codeleg.noteflow.R
import com.codeleg.noteflow.adapter.NoteAdapter
import com.codeleg.noteflow.database.NoteDao
import com.codeleg.noteflow.database.NoteDatabase
import com.codeleg.noteflow.databinding.FragmentHomeBinding
import com.codeleg.noteflow.model.Note
import com.codeleg.noteflow.utils.FragmentNavigation
import com.codeleg.noteflow.viewmodel.SharedNotesViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch
class HomeFragment : Fragment(), NoteAdapter.OnNoteClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var noteAdapter: NoteAdapter
    private val sharedNotesViewModel: SharedNotesViewModel by activityViewModels()
    private var navigation: FragmentNavigation? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        navigation = context as? FragmentNavigation
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        loadNotes()
        setupListeners()
    }

    private fun setupUI() {
        noteAdapter = NoteAdapter(requireContext(), mutableListOf(), this)
        binding.notesRecyclerView.adapter = noteAdapter
    }

    private fun setupListeners() {
        binding.addNotesFab.setOnClickListener {
            navigation?.navigateTo(AddNoteFragment(), null, true, "Add Note", R.menu.add_page_menu)
        }
    }

    private fun loadNotes() {
        sharedNotesViewModel.loadNotes { notes ->
            if (notes.isNotEmpty()) {
                noteAdapter.updateNotes(notes)
                binding.ivNoNote.visibility = View.GONE
            } else {
                binding.ivNoNote.visibility = View.VISIBLE
            }
        }
    }

    override fun onNoteClick(note: Note) {
        val bundle = Bundle().apply {
            putParcelable("note", note)
        }
        navigation?.navigateTo(EditFragment(), bundle, true, "Edit Note", R.menu.edit_page_menu)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
