package com.codeleg.noteflow.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.codeleg.noteflow.R
import com.codeleg.noteflow.database.NoteDao
import com.codeleg.noteflow.database.NoteDatabase
import com.codeleg.noteflow.databinding.FragmentAddNoteBinding
import com.codeleg.noteflow.model.Note
import com.codeleg.noteflow.utils.FragmentNavigation
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class AddNoteFragment : Fragment() {


    private  var AddNoteBinding: FragmentAddNoteBinding? = null
    private val binding get() = AddNoteBinding!!

    private lateinit var etAddNoteTitle: TextInputEditText
    private lateinit var etAddNoteDesc: TextInputEditText
    private lateinit var addBtn: FloatingActionButton
    private lateinit var NoteDB: NoteDatabase
    private lateinit var NoteDao: NoteDao
    private var navigation: FragmentNavigation? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        NoteDB = NoteDatabase.getDB(requireContext())
        NoteDao = NoteDB.getNoteDao()
        if (context is FragmentNavigation) {
            navigation = context
        }

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        AddNoteBinding = FragmentAddNoteBinding.inflate(inflater, container, false)
        etAddNoteTitle = binding.etAddNoteTitle
        etAddNoteDesc = binding.etAddNoteDesc
        addBtn = binding.fabAddNote
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addBtn.setOnClickListener {
            val title = etAddNoteTitle.text.toString()
            val desc = etAddNoteDesc.text.toString()
            if (title.isNotEmpty() || desc.isNotEmpty()) {
                val note = Note(0, title, desc)
                lifecycleScope.launch {
                NoteDao.insertNote(note)

                }
                etAddNoteTitle.text?.clear()
                etAddNoteDesc.text?.clear()

                navigation?.navigateTo(HomeFragment(), null, false)

            }
        }
    }


    override fun onDetach() {
        AddNoteBinding = null
        super.onDetach()
    }

}