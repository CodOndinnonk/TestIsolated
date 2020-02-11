package com.ondinnonk.testisolated.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ondinnonk.testisolated.NetRepository
import com.ondinnonk.testisolated.extensions.SingleLiveEvent
import kotlinx.coroutines.launch

class FilmsListViewModel : ViewModel() {

    private val repository = NetRepository.instance
    private var _adapterFilmsList: MutableLiveData<List<Film>> = MutableLiveData()
    private var _openFilmDetails = SingleLiveEvent<Film>()

    val adapterFilmsList = _adapterFilmsList
    val actionOnApplyFilter = MutableLiveData<Unit>()
    val openFilmDetails = _openFilmDetails
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
        _openFilmDetails.postValue(film)
    }
}
