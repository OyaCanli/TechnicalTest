package com.canlioya.technicaltest.ui.albums

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.canlioya.technicaltest.common.NetworkIdlingResource
import com.canlioya.technicaltest.data.IRepository
import com.canlioya.technicaltest.di.IODispatcher
import com.canlioya.technicaltest.model.Album
import com.canlioya.technicaltest.model.Result
import com.canlioya.technicaltest.model.UIState
import com.canlioya.technicaltest.ui.base.BaseViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class AlbumViewModel @ViewModelInject constructor(
    private val repository: IRepository,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : BaseViewModel() {

    private val _albums = MutableStateFlow<List<Album>>(emptyList())
    val albums: StateFlow<List<Album>>
        get() = _albums

    init {
        viewModelScope.launch(dispatcher) {
            startFetching()
        }
    }

    /**
     * Claim albums from the repository.
     * @return a Flow<Result<*>>
     * Results are wrapped in {@link com.canlioya.technicaltest.model.Result}
     * sealed class. UIState is u
     */
    override suspend fun startFetching() {
        NetworkIdlingResource.increment()
        repository.getAllAlbums().collect { result ->
            when (result) {
                is Result.Loading -> _uiState.value = UIState.LOADING
                is Result.Error -> {
                    _uiState.value = UIState.ERROR
                    NetworkIdlingResource.decrement()
                }
                is Result.Success -> {
                    _uiState.value = UIState.SUCCESS
                    processData(result.data)
                    NetworkIdlingResource.decrement()
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
            _albums.value = sortedList
        }
    }
}