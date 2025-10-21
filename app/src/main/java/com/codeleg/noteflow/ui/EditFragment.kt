package com.codeleg.noteflow.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.codeleg.noteflow.R
import com.codeleg.noteflow.database.NoteDao
import com.codeleg.noteflow.database.NoteDatabase
import com.codeleg.noteflow.databinding.FragmentEditBinding
import com.codeleg.noteflow.model.Note
import com.codeleg.noteflow.utils.FragmentNavigation
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class EditFragment : Fragment() {

    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!
    private lateinit var etEditNoteTitle: TextInputEditText
    private lateinit var etEditNoteDesc: TextInputEditText
    private var navigation: FragmentNavigation? = null
    private lateinit var NoteDao: NoteDao
    private lateinit var NoteDB: NoteDatabase
    private var currentNote: Note? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parentFragmentManager.setFragmentResultListener("update_note", this) { _, _ ->
            updateNoteLogic()
        }
        parentFragmentManager.setFragmentResultListener("delete_note_request", this) { _, _ ->
            deleteNoteLogic()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentNavigation) {
            navigation = context
        }
        NoteDB = NoteDatabase.getDB(requireContext())
        NoteDao = NoteDB.getNoteDao()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etEditNoteTitle = binding.etEditNoteTitle
        etEditNoteDesc = binding.etEditNoteDesc
        arguments?.let {
            val note = it.getParcelable<Note>("note")
            if (note != null) {
                currentNote = note
                etEditNoteTitle.setText(note.title)
                etEditNoteDesc.setText(note.description)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateNoteLogic(){
        val title = etEditNoteTitle.text.toString()
        val desc = etEditNoteDesc.text.toString()

        if (title.trim().isNotEmpty()) {
            val note = Note(currentNote?.id ?: 0, title, desc)
            lifecycleScope.launch {
                NoteDao.updateNote(note)
                navigation?.navigateTo(HomeFragment(), null, false , "Notes" , R.menu.home_page_menu)
            }

        }else{
            etEditNoteTitle.error = "Title cannot be empty"
        }
    }

    private fun deleteNoteLogic() {
        currentNote?.let { note ->
            lifecycleScope.launch {
                NoteDao.deleteNote(note)
                navigation?.navigateTo(HomeFragment(), null, false, "Notes", R.menu.home_page_menu)
            }
        }
    }

}
