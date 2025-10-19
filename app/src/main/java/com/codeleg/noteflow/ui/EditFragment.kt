package com.codeleg.noteflow.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.codeleg.noteflow.databinding.FragmentEditBinding
import com.codeleg.noteflow.model.Note
import com.codeleg.noteflow.utils.FragmentNavigation
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText

class EditFragment : Fragment() {

    private var editBinding: FragmentEditBinding? = null
    private val binding get() = editBinding!!
    private lateinit var etEditNoteTitle: TextInputEditText
    private lateinit var etEditNoteDesc: TextInputEditText
    private lateinit var btnSaveNote: FloatingActionButton
    private var navigation: FragmentNavigation? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        editBinding = FragmentEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentNavigation) {
            navigation = context
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etEditNoteTitle = binding.etEditNoteTitle
        etEditNoteDesc = binding.etEditNoteDesc
        btnSaveNote = binding.btnSaveNote

        arguments?.let {
            val note = it.getParcelable<Note>("note")
            if (note != null) {
                etEditNoteTitle.setText(note.title)
                etEditNoteDesc.setText(note.description)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        editBinding = null
    }
}
