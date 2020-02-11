package com.ondinnonk.testisolated.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ondinnonk.testisolated.R
import com.ondinnonk.testisolated.details.DetailsPagerFragment
import com.ondinnonk.testisolated.extensions.openFragment
import kotlinx.android.synthetic.main.fragment_films_list.*

class FilmsListFragment : Fragment() {

    private lateinit var viewModel: FilmsListViewModel

    companion object {
        fun newInstance(): FilmsListFragment {
            return FilmsListFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(FilmsListViewModel::class.java)

        return inflater.inflate(R.layout.fragment_films_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        films_list_search_cancel.visibility = View.GONE
        initObservers()
        initListeners()
    }

    private fun initListeners() {
        films_list_search_btn.setOnClickListener {
            if (films_list_search_input.text.isBlank().not()) {
                viewModel.applyFilter(films_list_search_input.text.toString())
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.empty_search_warning_text),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        films_list_search_cancel.setOnClickListener { cancelFilters() }
    }

    private fun initObservers() = with(viewModel) {
        filmsListAdapter.observe(viewLifecycleOwner, Observer {
            films_list_recycler.layoutManager = LinearLayoutManager(requireContext())
            films_list_recycler.adapter = it
            films_list_recycler.invalidate()
        })
        adapterFilmsList.observe(
            viewLifecycleOwner,
            Observer { filmsListAdapter.value?.setFilms(it) })
        actionOnApplyFilter.observe(
            viewLifecycleOwner,
            Observer { films_list_search_cancel.visibility = View.VISIBLE })
        openFilmDetails.observe(
            viewLifecycleOwner,
            Observer {
                activity?.openFragment(DetailsPagerFragment.newInstance(it))
            })
    }

    private fun cancelFilters() {
        films_list_search_cancel.visibility = View.GONE
        viewModel.fetchFilms()
        films_list_search_input.clearFocus()
        films_list_search_input.setText("")
    }
}