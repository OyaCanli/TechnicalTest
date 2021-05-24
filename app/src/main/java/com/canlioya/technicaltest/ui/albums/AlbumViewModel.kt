package com.canlioya.technicaltest.ui.albums

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.canlioya.technicaltest.data.IRepository
import com.canlioya.technicaltest.model.Album
import com.canlioya.technicaltest.model.Result
import com.canlioya.technicaltest.model.UIState
import com.canlioya.technicaltest.ui.base.BaseViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class AlbumViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val repository: IRepository
) : BaseViewModel() {

    private val _albums = MutableLiveData<List<Album>>()
    val albums: LiveData<List<Album>>
        get() = _albums

    init {
        startFetching()
    }

    /**
     * Claim albums from the repository.
     * @return a Flow<Result<*>>
     * Results are wrapped in {@link com.canlioya.technicaltest.model.Result}
     * sealed class. UIState is u
     */
    override fun startFetching() {
        viewModelScope.launch {
            repository.getAllAlbums().collect { result ->
                when (result) {
                    is Result.Loading -> _uiState.postValue(UIState.LOADING)
                    is Result.Error -> _uiState.postValue(UIState.ERROR)
                    is Result.Success -> {
                        _uiState.postValue(UIState.SUCCESS)
                        processData(result.data)
                    }
                }
            }
        }
    }

    private fun processData(data: List<Album>?) {
        if (data == null) {
            return
        }

        if (data.isNotEmpty()) {
            val sortedList = data.sortedBy {
                it.albumTitle
            }
            _albums.postValue(sortedList)
        } else {
            _albums.postValue(emptyList())
        }
    }
}