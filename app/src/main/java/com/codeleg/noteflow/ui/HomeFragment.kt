package com.codeleg.noteflow.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.codeleg.noteflow.adapter.NoteAdapter
import com.codeleg.noteflow.databinding.FragmentHomeBinding
import com.codeleg.noteflow.model.Note
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeFragment : Fragment(), NoteAdapter.OnNoteClickListener {

    private var homeBinding: FragmentHomeBinding? = null
    private val binding get() = homeBinding!!
    private lateinit var noteRecyclerView: RecyclerView
    private lateinit var noteAdapter: NoteAdapter
    private var navigation: FragmentNavigation? = null
    private lateinit var addNoteFab: FloatingActionButton



    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentNavigation) {
            navigation = context
        }
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
        addNoteFab.setOnClickListener {
            navigation?.navigateTo(AddNoteFragment(), null)
        }


        val dummyNotes = listOf(
            Note(1, "Meeting Notes", "Discussed Q3 roadmap and project timelines."),
            Note(2, "Grocery List", "Milk, Bread, Eggs, Cheese, Fruits"),
            Note(3, "Book Ideas", "A sci-fi novel about AI consciousness."),
            Note(4, "Workout Plan", "Monday: Chest, Tuesday: Back, Wednesday: Legs"),
            Note(5, "Recipe for Pasta", "Ingredients: Pasta, tomatoes, garlic, olive oil."),
            Note(6, "Gift Ideas for Mom", "Scarf, perfume, or a new book."),
            Note(7, "Learning Spanish", "Practice verb conjugations. Ser vs Estar."),
            Note(8, "Project Deadline", "Submit the final report by Friday EOD."),
            Note(9, "Vacation Plans", "Research flights to Italy for next summer."),
            Note(10, "Daily Reflection", "Today was productive. Finished the main feature.")
        )

        noteAdapter = NoteAdapter(requireContext(), dummyNotes, this)
        noteRecyclerView.adapter = noteAdapter
    }

    override fun onNoteClick(note: Note) {
        val bundle = Bundle().apply {
            putInt("noteId", note.id)
            putString("noteTitle", note.title)
            putString("noteDescription", note.description)
        }
        navigation?.navigateTo(EditFragment(), bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        homeBinding = null
    }
}
