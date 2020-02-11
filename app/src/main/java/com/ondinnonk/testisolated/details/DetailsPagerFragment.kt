package com.ondinnonk.testisolated.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ondinnonk.testisolated.R
import com.ondinnonk.testisolated.list.Film
import kotlinx.android.synthetic.main.fragment_details_pager.*

class DetailsPagerFragment : Fragment() {

    private lateinit var viewModel: DetailsPagerViewModel
    private lateinit var adapter: DetailsPagerAdapter

    companion object {
        private const val KEY_SELECTED_FILM = "selectedFilm"

        fun newInstance(selectedFilm: Film): DetailsPagerFragment {
            val bundle = Bundle()
            bundle.putParcelable(KEY_SELECTED_FILM, selectedFilm)
            val instance = DetailsPagerFragment()
            instance.arguments = bundle
            return instance
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(DetailsPagerViewModel::class.java)
        viewModel.firstSelectedFilm = arguments?.getParcelable(KEY_SELECTED_FILM)
        initAdapter()
        return inflater.inflate(R.layout.fragment_details_pager, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
    }

    private fun initAdapter() {
        adapter = DetailsPagerAdapter(childFragmentManager)
        viewModel.loadFilms()
    }

    private fun initViews() {
        details_pager.adapter = adapter
    }

    private fun initObservers() = with(viewModel) {
        loadedFilmsList.observe(viewLifecycleOwner, Observer {
            adapter.setItemsData(it)
            details_pager.setCurrentItem(getFirstSelectedFilmPosition(), true)
        })
    }
}