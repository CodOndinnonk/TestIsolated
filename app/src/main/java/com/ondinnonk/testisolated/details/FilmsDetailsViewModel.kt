package com.ondinnonk.testisolated.details

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ondinnonk.testisolated.NetRepository
import com.ondinnonk.testisolated.list.Film
import kotlinx.coroutines.launch

class FilmsDetailsViewModel : ViewModel() {

    private val repository = NetRepository.instance
    private var _readyImage = MutableLiveData<Bitmap?>()
    private var _noFilmData = MutableLiveData<Unit>()

    val noFilmData = _noFilmData
    val readyImage = _readyImage
    var film: Film? = null

    fun setImage() {
        film?.let {
            viewModelScope.launch {
                readyImage.postValue(repository.getFilmImage(it.imageURL))
            }
        } ?: noFilmData.postValue(Unit)
    }

}
