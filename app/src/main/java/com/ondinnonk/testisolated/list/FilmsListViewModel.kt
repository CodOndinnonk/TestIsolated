package com.ondinnonk.testisolated.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ondinnonk.testisolated.Config
import com.ondinnonk.testisolated.utils.NetLoader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FilmsListViewModel : ViewModel() {

    var filmsList: MutableLiveData<List<Film>> = MutableLiveData()


    val filmsListAdapter: MutableLiveData<FilmsListAdapter> =
        MutableLiveData(FilmsListAdapter(this::onFilmSelect))

    init {
        fetchFilms()
    }

    fun fetchFilms() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                NetLoader().getJSON(Config.SOURCE_URL)?.let {
                    filmsList.postValue(Film.create(it))
                }
            }
        }
    }

    private fun onFilmSelect(film: Film) {

    }


}
