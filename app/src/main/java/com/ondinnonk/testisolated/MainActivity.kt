package com.ondinnonk.testisolated

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.ondinnonk.testisolated.extensions.openFragment
import com.ondinnonk.testisolated.list.ListFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val CONTAINER_ID = R.id.main_container
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNavigationMenu()
    }

    override fun onResume() {
        super.onResume()
        openFragment(ListFragment())
    }

    private fun setupNavigationMenu() {
        setSupportActionBar(main_toolbar)

        val toggle = ActionBarDrawerToggle(
            this,
            main_drawer_layout,
            main_toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        main_drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
    }


}