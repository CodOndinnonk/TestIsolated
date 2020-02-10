package com.ondinnonk.testisolated.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ondinnonk.testisolated.Config
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FilmsListViewModel : ViewModel() {

    private var _filmsList: MutableLiveData<List<Film>> = MutableLiveData()
    val filmsList = _filmsList
    private val repository: NetRepository by lazy { NetRepository() }


    val filmsListAdapter: MutableLiveData<FilmsListAdapter> =
        MutableLiveData(FilmsListAdapter(this::onFilmSelect))

    init {
        fetchFilms()
    }

    fun fetchFilms() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.getJSON(Config.SOURCE_URL)?.let {
                    _filmsList.postValue(Film.create(it))
                }
            }
        }
    }

    private fun onFilmSelect(film: Film) {

    }


}
