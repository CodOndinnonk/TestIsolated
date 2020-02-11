package com.ondinnonk.testisolated.details

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.ondinnonk.testisolated.list.Film

class DetailsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private var filmsData = arrayListOf<Film>()

    override fun getItem(position: Int): Fragment {
        return FilmDetailsFragment.newInstance(filmsData[position])
    }

    override fun getCount(): Int {
        if (filmsData.size < 1) {
            Log.w(this::javaClass.name, "List for pager is empty. You need to fill it before call.")
        }
        return filmsData.size
    }

    fun setItemsData(list: List<Film>) {
        filmsData.clear()
        filmsData.addAll(list)
        notifyDataSetChanged()
    }
}