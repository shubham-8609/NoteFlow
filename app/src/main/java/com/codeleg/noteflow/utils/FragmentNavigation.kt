package com.codeleg.noteflow.utils

import android.os.Bundle
import androidx.fragment.app.Fragment

interface FragmentNavigation {
    fun navigateTo(fragment: Fragment, args: Bundle? = null, addToBackStack: Boolean = false)
}