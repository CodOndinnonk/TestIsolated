package com.ondinnonk.testisolated.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class FilmsListViewModel : ViewModel() {

    var filmsList: MutableLiveData<ArrayList<Film>> = MutableLiveData(
        arrayListOf(
            Film(0, "0", "", "000", 1457018867393),
            Film(1, "1", "", "111", 1457018867344),
            Film(2, "2", "", "222", 1457018867553),
            Film(3, "3", "", "333", 1457012224546),
            Film(4, "4", "", "444", 1457018861212),
            Film(5, "5", "", "555", 1457018867421)
        )
    )

    val filmsListAdapter: MutableLiveData<FilmsListAdapter> =
        MutableLiveData(FilmsListAdapter(this::onFilmSelect))

    private fun onFilmSelect(film: Film) {

    }
}
