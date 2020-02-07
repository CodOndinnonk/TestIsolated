package com.ondinnonk.testisolated.extensions

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.ondinnonk.testisolated.MainActivity

fun FragmentActivity.openFragment(openFragment: Fragment) {
    getFragmentContainerId(this)?.let {
        this.supportFragmentManager
            .beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .replace(it, openFragment)
            .addToBackStack(openFragment::class.java.name)
            .commit()
    } ?: Log.e(this.javaClass.name, "Unexpected host activity. Can't open fragment.")
}

private fun getFragmentContainerId(activity: FragmentActivity): Int? {
    when (activity) {
        is MainActivity -> return MainActivity.CONTAINER_ID
        else -> return null
    }
}
