package com.canlioya.technicaltest.ui.photos

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.canlioya.technicaltest.data.IRepository
import com.canlioya.technicaltest.model.Album
import com.canlioya.technicaltest.model.Photo
import com.canlioya.technicaltest.model.Result
import com.canlioya.technicaltest.ui.base.BaseViewModel
import com.canlioya.technicaltest.model.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PhotoViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val repository: IRepository
) : BaseViewModel() {


    private val _photos = MutableStateFlow<List<Photo>>(emptyList())
    val photos: StateFlow<List<Photo>>
        get() = _photos

    val album = savedStateHandle.get<Album>("chosenAlbum")

    init {
        startFetching()
    }

    override fun startFetching() {
        viewModelScope.launch {
            album?.albumId?.let {
                repository.getPhotosForAlbum(it).collect { result ->
                    when (result) {
                        is Result.Loading -> _uiState.value = UIState.LOADING
                        is Result.Error -> _uiState.value = UIState.ERROR
                        is Result.Success -> {
                            _uiState.value =UIState.SUCCESS
                            if (result.data?.isNotEmpty() == true) {
                                _photos.value = result.data
                            }
                        }
                    }
                }
            }
        }
    }

}

