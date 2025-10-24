package com.codeleg.noteflow.ui

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.codeleg.noteflow.R
import com.codeleg.noteflow.databinding.ActivityMainBinding
import com.codeleg.noteflow.utils.FragmentNavigation
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import com.codeleg.noteflow.database.NoteDao
import com.codeleg.noteflow.database.NoteDatabase
import com.codeleg.noteflow.utils.DialogHelper
import com.codeleg.noteflow.viewmodel.SharedNotesViewModel
import kotlinx.coroutines.launch
import kotlin.getValue

class MainActivity : AppCompatActivity(), FragmentNavigation {

    private lateinit var binding: ActivityMainBinding
    private var currentMenuId: Int = 0
    private val notesViewModel: SharedNotesViewModel by viewModels()
    private val noteDao: NoteDao by lazy {
        NoteDatabase.getDB(this).getNoteDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater).apply { setContentView(root) }
        handleInsets()
        manageToolbar()
        if (savedInstanceState == null) {
            navigateTo(HomeFragment(), null, false, "Notes", R.menu.home_page_menu)
        }
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (supportFragmentManager.backStackEntryCount > 0) {
                    navigateTo(HomeFragment(), null, false, "Notes", R.menu.home_page_menu)
                    supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                } else {
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        })

    }

    private fun handleInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { view, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(bars.left, bars.top, bars.right, bars.bottom)
            insets
        }
    }

    override fun navigateTo(
        fragment: Fragment,
        args: Bundle?,
        addToBackStack: Boolean,
        subtitle: String,
        menuId: Int
    ) {
        fragment.arguments = args
        with(supportFragmentManager.beginTransaction()) {
            setCustomAnimations(
                android.R.anim.fade_in,
                android.R.anim.fade_out,
                android.R.anim.fade_in,
                android.R.anim.fade_out
            )
            replace(R.id.main_container, fragment)
            if (addToBackStack) addToBackStack(fragment::class.java.simpleName)
            commit()
            updateToolbar(subtitle, menuId)
        }
    }

    override fun updateToolbar(subtitle: String, menuId: Int) {
        supportActionBar?.title = title
        supportActionBar?.subtitle = subtitle
        if (this.currentMenuId != menuId) {
            this.currentMenuId = menuId
            invalidateOptionsMenu()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete_all_option -> {
                DialogHelper.showConfirmDialog(
                    this@MainActivity,
                    "Delete all notes",
                    "Are you sure you want to delete all notes ??",
                    "Yes",
                    "No",
                    onConfirm = {
                        lifecycleScope.launch {
                            noteDao.deleteAllNotes()
                            navigateTo(HomeFragment(), null, false, "Notes", R.menu.home_page_menu)
                        }
                    })
            }

            R.id.discard_note_option -> navigateTo(
                HomeFragment(),
                null,
                false,
                "Notes",
                R.menu.home_page_menu
            )

            R.id.update_note_option -> supportFragmentManager.setFragmentResult(
                "update_note",
                Bundle.EMPTY
            )

            R.id.save_note_option -> supportFragmentManager.setFragmentResult(
                "save_note",
                Bundle.EMPTY
            )

            R.id.delete_from_edit -> {
                DialogHelper.showConfirmDialog(
                    this@MainActivity,
                    "Delete this note ?",
                    "Are you sure you want to delete note ??",
                    "Yes",
                    "No",
                    onConfirm = {
                        supportFragmentManager.setFragmentResult(
                            "delete_note_request",
                            Bundle.EMPTY
                        )
                    }
                )
            }

        }
        return true
    }

    private fun manageToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.subtitle = "Notes"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (currentMenuId != 0) {
            menuInflater.inflate(currentMenuId, menu)
        } else {
            menuInflater.inflate(R.menu.home_page_menu, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }


}
