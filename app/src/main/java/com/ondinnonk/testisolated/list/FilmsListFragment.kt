package com.ondinnonk.testisolated.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ondinnonk.testisolated.R
import kotlinx.android.synthetic.main.fragment_films_list.*

class FilmsListFragment : Fragment() {
    private lateinit var viewModel: FilmsListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FilmsListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_films_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
    }

    private fun initObservers() = with(viewModel) {
        filmsListAdapter.observe(viewLifecycleOwner, Observer {
            films_list_recycler.layoutManager = LinearLayoutManager(requireContext())
            films_list_recycler.adapter = it
            films_list_recycler.invalidate()
        })
        filmsList.observe(viewLifecycleOwner, Observer { filmsListAdapter.value?.setFilms(it) })
    }
}