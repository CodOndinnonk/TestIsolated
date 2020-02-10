package com.ondinnonk.testisolated

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.ondinnonk.testisolated.extensions.openFragment
import com.ondinnonk.testisolated.list.FilmsListFragment
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
        openFragment(FilmsListFragment())
    }

    private fun setupNavigationMenu() {
        setSupportActionBar(main_toolbar)

        //if use horizontal layout don't have drawer
        main_drawer_layout?.let {
            val toggle = ActionBarDrawerToggle(
                this,
                it,
                main_toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
            )
            it.addDrawerListener(toggle)
            toggle.syncState()
        }

        main_navigation_view.setNavigationItemSelectedListener {
            openFragment(FilmsListFragment())
            return@setNavigationItemSelectedListener true
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (main_container.childCount == 0){
            finish()
        }

    }

}
