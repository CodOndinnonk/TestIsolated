package com.ondinnonk.testisolated.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ondinnonk.testisolated.Config
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FilmsListViewModel : ViewModel() {

    private val repository: NetRepository by lazy { NetRepository() }

    private var originFilmList = arrayListOf<Film>()
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
            withContext(Dispatchers.IO) {
                repository.getJSON(Config.SOURCE_URL)?.let {
                    originFilmList.clear()
                    originFilmList.addAll(Film.create(it))
                    _adapterFilmsList.postValue(originFilmList)
                }
            }
        }
    }

    fun applyFilter(searchName: String) {
        val out = arrayListOf<Film>()
        originFilmList.forEach {
            if (it.name.contains(searchName, true)) {
                out.add(it)
            }
        }
        _adapterFilmsList.postValue(out)
        actionOnApplyFilter.postValue(Unit)
    }

    private fun onFilmSelect(film: Film) {

    }


}
