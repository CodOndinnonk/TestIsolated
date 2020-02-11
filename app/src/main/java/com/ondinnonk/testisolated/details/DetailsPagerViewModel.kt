package com.ondinnonk.testisolated.details

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ondinnonk.testisolated.NetRepository
import com.ondinnonk.testisolated.list.Film
import kotlinx.coroutines.launch

class DetailsPagerViewModel : ViewModel() {

    private val repository = NetRepository.instance
    private var _loadedFilmsList = MutableLiveData<List<Film>>()

    var firstSelectedFilm: Film? = null
    val loadedFilmsList = _loadedFilmsList


    fun loadFilms() {
        viewModelScope.launch {
            _loadedFilmsList.postValue(repository.getFilmsList())
        }
    }

    fun getFirstSelectedFilmPosition(): Int {
        firstSelectedFilm?.let { f ->
            loadedFilmsList.value?.let { lf ->
                if (lf.contains(f)) {
                    return lf.indexOf(f)
                }
            }
        }
        Log.w(
            this::javaClass.name,
            "Fail to get selected film position, pager will start to 0 position"
        )
        return 0
    }
}
