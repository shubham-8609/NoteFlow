package com.codeleg.noteflow.ui

import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.codeleg.noteflow.R
import com.codeleg.noteflow.databinding.ActivityMainBinding
import com.codeleg.noteflow.utils.FragmentNavigation
import androidx.activity.OnBackPressedCallback

class MainActivity : AppCompatActivity(), FragmentNavigation {

    private lateinit var binding: ActivityMainBinding
    private var currentMenuId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater).apply { setContentView(root) }
        handleInsets()
        manageToolbar()
        if (savedInstanceState == null) {
            navigateTo(HomeFragment(),  null , false)
        }
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (supportFragmentManager.backStackEntryCount > 0) {
                    supportFragmentManager.popBackStack()
                    updateToolbar("NoteFlow", "Notes", R.menu.home_page_menu)
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

    override fun navigateTo(fragment: Fragment, args: Bundle?, addToBackStack: Boolean) {
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
        }
    }

    override fun updateToolbar(title: String, subtitle: String, menuId: Int) {
        supportActionBar?.title = title
        supportActionBar?.subtitle = subtitle
        if (this.currentMenuId != menuId) {
            this.currentMenuId = menuId
            invalidateOptionsMenu()
        }
    }

    private fun manageToolbar(){
        setSupportActionBar(binding.toolbar)
        supportActionBar?.subtitle = "Notes"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (currentMenuId != 0) {
            menuInflater.inflate(currentMenuId, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }
    
}
