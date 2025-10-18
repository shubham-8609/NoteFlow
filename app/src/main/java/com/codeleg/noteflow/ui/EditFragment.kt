package com.codeleg.noteflow.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.codeleg.noteflow.databinding.FragmentEditBinding

class EditFragment : Fragment() {

    private var editBinding: FragmentEditBinding? = null
    private val binding get() = editBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        editBinding = FragmentEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val noteId = it.getInt("noteId")
            val noteTitle = it.getString("noteTitle")
            val noteDescription = it.getString("noteDescription")

            binding.etEditNoteTitle.setText(noteTitle)
            binding.etEditNoteDesc.setText(noteDescription)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        editBinding = null
    }
}
