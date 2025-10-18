package com.codeleg.noteflow.ui

import android.os.Bundle

interface FragmentNavigation {
    fun navigateTo(fragment: androidx.fragment.app.Fragment, args: Bundle? = null)
}
