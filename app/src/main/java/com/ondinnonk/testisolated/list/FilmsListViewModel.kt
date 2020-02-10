package com.ondinnonk.testisolated.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FilmsListViewModel : ViewModel() {

    private val repository: NetRepository by lazy { NetRepository() }

    private var _adapterFilmsList: MutableLiveData<List<Film>> = MutableLiveData()
    val adapterFilmsList = _adapterFilmsList
    val actionOnApplyFilter = MutableLiveData<Unit>()


    val filmsListAdapter: MutableLiveData<FilmsListAdapter> =
        MutableLiveData(FilmsListAdapter(this::onFilmSelect))

    init {
        fetchFilms()
    }

    fun fetchFilms() {
        viewModelScope.launch {
                _adapterFilmsList.postValue(repository.getFilmsList())
        }
    }

    fun applyFilter(searchName: String) {
        val out = arrayListOf<Film>()
        viewModelScope.launch {
            repository.getFilmsList().forEach {
                if (it.name.contains(searchName, true)) {
                    out.add(it)
                }
            }
        }
        _adapterFilmsList.postValue(out)
        actionOnApplyFilter.postValue(Unit)
    }

    private fun onFilmSelect(film: Film) {

    }


}
