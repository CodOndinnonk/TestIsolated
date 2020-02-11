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
import com.ondinnonk.testisolated.utils.DateUtil
import kotlinx.android.synthetic.main.fragment_film_details.*

class FilmDetailsFragment : Fragment() {

    private lateinit var viewModel: FilmsDetailsViewModel

    companion object {
        private const val KEY_FILM = "film"

        fun newInstance(film: Film): FilmDetailsFragment {
            val bundle = Bundle()
            bundle.putParcelable(KEY_FILM, film)
            val instance = FilmDetailsFragment()
            instance.arguments = bundle
            return instance
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(FilmsDetailsViewModel::class.java)
        viewModel.film = arguments?.getParcelable(KEY_FILM)
        return inflater.inflate(R.layout.fragment_film_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initViews()
    }

    private fun initObservers() = with(viewModel) {
        readyImage.observe(viewLifecycleOwner, Observer { film_details_image.setImageBitmap(it) })
        noFilmData.observe(
            viewLifecycleOwner,
            Observer { film_details_name.text = getString(R.string.film_details_error_text) })
    }

    private fun initViews() {
        viewModel.setImage()
        viewModel.film?.let {
            with(it) {
                film_details_id.text = id.toString()
                film_details_name.text = name
                film_details_date.text =
                    DateUtil.getDate(date, DateUtil.dd_MMMM_yyyy_HH_MM)
                film_details_description.text = description
            }
        }
    }
}